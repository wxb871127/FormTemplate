package template.bean;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.Closeable;
import java.lang.reflect.Field;

import base.util.CommonUtil;
import base.util.ReflectUtil;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;

public abstract class BaseTemplate {
    public String name;//字段名
    public String label;//标题
    public String required;//必填项
    public String unit;//单位
    public String editable;//是否可编辑的表达式
    public String show;//是否显示的表达式
    protected SectionTemplate sectionTemplate;//所属section

    public void parseElement(Element e) {
        Element element = (Element) e.cloneNode(true);

        Field[] fields = getClass().getFields();
        for (Field field : fields) {
            Attr attr = element.getAttributeNode(field.getName());
            if (attr != null) {
                ReflectUtil.setFiled(this, field, attr.getValue());
            }
        }
    }

    public void setSectionTemplate(SectionTemplate sectionTemplate) {
        this.sectionTemplate = sectionTemplate;
    }

    public String getShowName(Object object){
        if(object == null)
            return "";
        return object.toString();
    }


}
