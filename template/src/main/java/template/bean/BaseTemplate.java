package template.bean;

import android.content.Context;
import android.text.TextUtils;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.lang.reflect.Field;

import base.annotation.AttrTemplate;
import base.util.ReflectUtil;
import template.widget.tree.annotation.TreeNodeId;
import template.widget.tree.annotation.TreeNodeParentId;

public abstract class BaseTemplate {
    @TreeNodeId
    public String name;//字段名
    @TreeNodeParentId
    public String parentName;//父节点的字段名
    public String label;//标题
    public String required;//必填项
    public String unit;//单位
    public String editable;//是否可编辑的表达式
    public String show;//是否显示的表达式
    public String initValue;//初始化值
    public boolean useinitValue;
    public String value;//表达式计算字段值
    public String exception;//是否异常表达式 对应表单配置
    protected SectionTemplate sectionTemplate;//所属section
    public String command;//按钮和自定义view中使用
    public String hint;//输入和button中使用
    public String dataSource;//数据来源（设备数据）
    public boolean expression;//当前字段是否包含表达式
    public int position;

    public void parseElement(Element e) {
        Element element = (Element) e.cloneNode(true);

        Field[] fields = getClass().getFields();
        for (Field field : fields) {
            Attr attr = element.getAttributeNode(field.getName());
            if (attr != null) {
                ReflectUtil.setFiled(this, field, attr.getValue());
            }
        }
        setExpression();
    }

    private void setExpression() {
        if (!TextUtils.isEmpty(exception) && !"true".equals(exception) && !"false".equals(exception)) {
            expression = true;
            return;
        }

        if (!TextUtils.isEmpty(value)) {
            expression = true;
            return;
        }

        if (!TextUtils.isEmpty(editable) && !"true".equals(editable) && !"false".equals(editable)) {
            expression = true;
            return;
        }

        if (!TextUtils.isEmpty(show) && !"true".equals(show) && !"false".equals(show)) {
            expression = true;
            return;
        }
    }

    public void setSectionTemplate(SectionTemplate sectionTemplate) {
        this.sectionTemplate = sectionTemplate;
    }

    public String getShowName(Object object, Context context) {
        if (object == null || "null".equalsIgnoreCase(object.toString()))
            return "";
        return object.toString();
    }

    public boolean hasExceptionExpression() {
        if (!TextUtils.isEmpty(exception) && !"true".equals(exception) && !"false".equals(exception)) {
            return true;
        }
        return false;
    }


}
