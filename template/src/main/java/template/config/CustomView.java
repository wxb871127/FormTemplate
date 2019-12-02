package template.config;

import android.content.Context;
import java.util.Map;
import base.util.TemplateList;
import template.bean.CustomTemplate;
import template.interfaces.OnTemplateListener;
import template.widget.BaseViewHolder;

public interface CustomView {
    int getLayout();

    void initView(Context context, BaseViewHolder holder, Map valueMap, TemplateList templates, CustomTemplate template, Map attrMap, OnTemplateListener listener);

}
