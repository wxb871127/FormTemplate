package template.bean;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import org.greenrobot.greendao.database.Database;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

import base.annotation.Template;
import template.com.templatedb.DaoManager;
import base.util.TemplateParse;

@Template(tag = "search")
public class SearchTemplate extends BaseTemplate{
    public String style;//数据库表名
    public String uri;//ContentProvider Uri
    public String showFormat;
    public String selection;
    public String showColumn = null;//select要查询的字段名
    public String primaryKey;//主键
    public Map<String, String> nameMap;// 存放template_styles.xml 的 <name:value>键值对

    @Override
    public void parseElement(Element e) {
        super.parseElement(e);
        nameMap = new HashMap<>();

        if (!TextUtils.isEmpty(style)) {
            Element element = TemplateParse.getTemplateStyleElement();
            NodeList nodeList = element.getElementsByTagName("style");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element styleElement = (Element) nodeList.item(i);
                if (style.equals(styleElement.getAttribute("name"))) {
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

    @Override
    public String getShowName(Object object, Context context) {
        if(object == null || TextUtils.isEmpty(object.toString()))
            return "";
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(uri), null,
                primaryKey+" = ?", new String[]{object.toString()}, null, null);
        cursor.moveToFirst();
        String showname = "";
        int column = cursor.getColumnIndex(showColumn);
        if(column == -1) {
            cursor.close();
            return "";
        }
        showname = cursor.getString(column);
        cursor.close();
        if(showFormat!=null && !TextUtils.isEmpty(showFormat))
            showname = String.format(showFormat, showname);
        return showname;
    }
}
