package template.bean;

public class TemplateValue {
    public Object value;//真实值
    public String showValue;//显示值
    public String exceptionDesc;//异常描述
    @base.annotation.AttrTemplate(attr = "refuse")
    public Boolean refuse = false;//是否拒绝
    @base.annotation.AttrTemplate(attr = "exception")
    public Boolean exception = false;//是否异常 对应产生的数据
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
