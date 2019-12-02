package template.interfaces;

import java.util.Map;

import template.bean.BaseTemplate;

public interface OnTemplateListener {
    void onDataChanged(BaseTemplate key, Object value);//数据发生改变
    void onAttrChanged(BaseTemplate key, String attr, Object value);//属性发生改变
    void onDatasChanged(Map<String, Object> map);//多个数据发生改变
}
