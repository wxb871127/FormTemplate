package template.bean;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import base.annotation.Template;

@Template(tag = "radio")
public class RadioTemplate extends BaseTemplate{
    protected Map<String, String> codeMap;
    protected List<String> codes;

    public RadioTemplate(){
        codeMap = new LinkedHashMap<>();
        codes = new ArrayList<>();
    }

    @Override
    public void parseElement(Element e) {
        super.parseElement(e);
        NodeList items = e.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);
            putItem(item.getAttribute("code"), item.getAttribute("name"));
            codes.add(item.getAttribute("code"));
        }
    }

    private void putItem(String code, String name){
        codeMap.put(code, name);
    }

    public String[] getNames() {
        return codeMap.values().toArray(new String[0]);
    }

    public String getNameByCode(String code){
        return codeMap.get(code);
    }

    public String getCode(String name){
        Iterator entries = codeMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            if(name.equals(value))
                return key;
        }
        return "";
    }

    @Override
    public String getShowName(Object object) {
        if(object == null)
            return "";
        return getNameByCode(object.toString());
    }
}
