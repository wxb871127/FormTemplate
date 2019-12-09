package template.bean;

import android.text.TextUtils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

import base.annotation.Template;
import base.util.TemplateParse;

@Template(tag = "input")
public class InputTemplate extends BaseTemplate{
    public String inputType;
    public int maxLength;
    public String quote;//引用语 引用常用语库
    public String decimalFormat;//小数点格式 如“0.00”
    public String selection;
    public String showColumn = null;//select要查询的字段名
    public String primaryKey;//主键
    public Map<String, String> nameMap;// 存放template_styles.xml 的 <name:value>键值对
    public Map<String, String> columnMap;
    public String uri;//ContentProvider Uri

    @Override
    public void parseElement(Element e) {
        super.parseElement(e);

        //解析style.xml的相关属性
        nameMap = new HashMap<>();
        if ("true".equals(quote)) {
            Element element = TemplateParse.getTemplateStyleElement();
            NodeList nodeList = element.getElementsByTagName("style");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element styleElement = (Element) nodeList.item(i);
                if ("CommonExpression".equals(styleElement.getAttribute("name"))) {
                    uri = styleElement.getAttribute("uri");
                    NodeList items = styleElement.getElementsByTagName("item");
                    for (int j = 0; j < items.getLength(); j++) {
                        Element item = (Element) items.item(j);
                        String name = item.getAttribute("name");
                        nameMap.put(name, item.getAttribute("value"));
                    }
                    break;
                }
            }
        }
        showColumn = nameMap.get("showColumn");
        selection = nameMap.get("selection");
        primaryKey = nameMap.get("primaryKey");
    }
}
