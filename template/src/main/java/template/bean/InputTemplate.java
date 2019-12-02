package template.bean;

import org.w3c.dom.Element;

import base.annotation.Template;

@Template(tag = "input")
public class InputTemplate extends BaseTemplate{
    public String inputType;
    public int maxLength;
    public String quote;//引用语 引用常用语库
    public String decimalFormat;//小数点格式 如“0.00”

    @Override
    public void parseElement(Element e) {
        super.parseElement(e);
    }
}
