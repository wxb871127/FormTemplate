package template.bean;

import android.content.Context;
import android.text.TextUtils;

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
    protected Map<String, Object> codeMap;
    protected List<String> codes;
    protected List<Item> showItem;//显示的item项
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
            Item item1 = new Item();
            item1.name =  item.getAttribute("name");
            item1.show = item.getAttribute("show");
            putItem(item.getAttribute("code"),  item1);
            codes.add(item.getAttribute("code"));
        }
    }

    private void putItem(String code, Item item){
        codeMap.put(code, item);
    }

    public Map getCodeMap(){
        return codeMap;
    }

    public void setShowItem(List<Item> items){
        this.showItem = items;
    }

    public String[] getshowItemNames(){
        if(showItem == null || showItem.size() <= 0)
            return null;
        String[] strings = new String[showItem.size()];
        for(int i =0; i<showItem.size(); i++){
            strings[i] = showItem.get(i).name;
        }
        return strings;
    }

    public String[] getNames() {
        Item[]  items = codeMap.values().toArray(new Item[0]);
        if(items.length <= 0) return null;
        String[] strings = new String[items.length];
        for(int i=0; i<items.length; i++){
            strings[i] = items[i].name;
        }
        return strings;
    }

    public String getNameByCode(String code){
        if(code == null || TextUtils.isEmpty(code))
            return null;
        return ((Item)codeMap.get(code)).name;
    }

    public String getCode(String name){
        Iterator entries = codeMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            Item item = (Item) entry.getValue();
            String value = item.name;
            if(name.equals(value))
                return key;
        }
        return "";
    }

    @Override
    public String getShowName(Object object, Context context) {
        if(object == null)
            return "";
        return getNameByCode(object.toString());
    }
}
