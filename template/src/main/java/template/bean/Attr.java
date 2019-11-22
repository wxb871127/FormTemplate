package template.bean;

/**
 *  用于保存字段属性 （便于拒检、异常等属性扩展）
 */
public class Attr {
    @base.annotation.AttrTemplate(attr = "refuse")
    public Boolean isRefuse;//是否拒绝
    @base.annotation.AttrTemplate(attr = "exception")
    public Boolean isException;//是否异常 对应产生的数据
    public Boolean editable;//是否可编辑

    public Attr(Boolean isRefuse, Boolean isException, Boolean editable){
        this.isRefuse = isRefuse;
        this.isException = isException;
        this.editable = editable;
    }
}
