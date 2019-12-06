package template.interfaces;

import java.util.Map;

import template.bean.BaseTemplate;

public interface OnTemplateListener {
    void onDataChanged(BaseTemplate key, Object value, boolean notify);//数据发生改变
    void onAttrChanged(BaseTemplate key, String attr, Object value, boolean notify);//属性发生改变
    void onDatasChanged(Map<String, Object> map, boolean notify);//多个数据发生改变
}
