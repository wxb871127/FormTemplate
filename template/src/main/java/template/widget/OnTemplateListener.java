package template.widget;

import template.bean.BaseTemplate;

public interface OnTemplateListener {
    void onDataChange(BaseTemplate template, Object object);
    void onAttrClick(BaseTemplate template, String attrName);
}
