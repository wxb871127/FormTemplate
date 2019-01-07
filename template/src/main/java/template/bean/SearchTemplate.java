package template.bean;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

import base.annotation.Template;
import template.com.templatedb.DaoManager;
import util.TemplateParse;

@Template(tag = "search")
public class SearchTemplate extends BaseTemplate{
    public String style;//数据库表名
    public String showFormat;
    private String showColumn = null;//对应显示字段的数据库表列名
    public Map<String, String> columnMap;//存放 item中的<name,column>键值对，可以用来给与name值一致的字段赋值
    public Map<String, String> nameMap;// 存放template_styles.xml 的 <name:value>键值对

    @Override
    public void parseElement(Element e) {
        super.parseElement(e);
        nameMap = new HashMap<>();
        columnMap = new HashMap<>();

        if (!TextUtils.isEmpty(style)) {
            Element element = TemplateParse.getTemplateStyleElement();
            NodeList nodeList = element.getElementsByTagName("style");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element styleElement = (Element) nodeList.item(i);
                if (style.equals(styleElement.getAttribute("name"))) {
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

        NodeList nodeList = e.getElementsByTagName("item");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element item = (Element) nodeList.item(i);
            String column = item.getAttribute("column");
            String name = item.getAttribute("name");
            columnMap.put(name, column);
            if(showColumn == null)
                showColumn = column;
        }
    }

    @Override
    public String getShowName(Object object, Context context) {
        if(object == null || TextUtils.isEmpty(object.toString()))
            return "";
        Database database = DaoManager.getInstance(context).getDaoSession().getDatabase();
        String where = "select " + showColumn + " from " + style + " where " + nameMap.get("selectionShow");
        Cursor cursor = database.rawQuery(where,  new String[]{object.toString()});
        cursor.moveToFirst();
        String showname = cursor.getString(cursor.getColumnIndex(showColumn));
        cursor.close();
        if(showFormat!=null && !TextUtils.isEmpty(showFormat))
            showname = String.format(showFormat, showname);
        return showname;
    }
}
