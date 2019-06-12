package template.bean;

import android.content.Context;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.lang.reflect.Field;

import base.util.ReflectUtil;

public abstract class BaseTemplate {
    public String name;//字段名
    public String label;//标题
    public String required;//必填项
    public String unit;//单位
    public String editable;//是否可编辑的表达式
    public String show;//是否显示的表达式
    public String initValue;//初始化值
    public String value;//表达式计算字段值
    public String exception;//是否异常表达式 对应表单配置
    public boolean isRefuse;//是否拒绝
    public boolean isException;//是否异常 对应产生的数据

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

    public String getShowName(Object object, Context context){
        if(object == null || "null".equalsIgnoreCase(object.toString()))
            return "";
        return object.toString();
    }


}
