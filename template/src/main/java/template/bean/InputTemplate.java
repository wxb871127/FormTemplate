package template.bean;

import org.w3c.dom.Element;

import base.annotation.Template;

@Template(tag = "input")
public class InputTemplate extends BaseTemplate{
    public String hint;
    public String inputType;
    public int maxLength;
    public String unit;//字段单位

    @Override
    public void parseElement(Element e) {
        super.parseElement(e);
    }
}
