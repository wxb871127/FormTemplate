package template.bean;

import template.view.TemplateView;

public class TemplateValue {
    public Object value;//真实值
    public String showValue;//显示值
    @base.annotation.AttrTemplate(attr = "refuse")
    public Boolean refuse;//是否拒绝
    @base.annotation.AttrTemplate(attr = "exception")
    public Boolean exception;//是否异常 对应产生的数据
    public Boolean editable;//是否可编辑

    public TemplateValue(Object value, String show, Boolean refuse, Boolean exception, Boolean editable){
        this.value = value;
        this.showValue = show;
        this.refuse = refuse;
        this.exception = exception;
        this.editable = editable;
    }

    public TemplateValue(String show, Boolean refuse, Boolean exception, Boolean editable){
        this(null, show, refuse, exception, editable);
    }

    public TemplateValue(){

    }

}
