package template.config;

import android.content.Context;
import java.util.Map;
import base.util.TemplateList;
import template.bean.CustomTemplate;
import template.bean.TemplateValue;
import template.interfaces.OnTemplateListener;
import template.widget.BaseViewHolder;

public abstract class CustomView {
    protected int getLayout(){
        return -1;
    }

    protected int getSpinnerLayout(){
        return 0;
    }

    protected int getContentLayout(){
        return 0;
    }

    public abstract void initView(Context context, BaseViewHolder holder, Map<String, TemplateValue> valueMap,
                                     TemplateList templates, CustomTemplate template, Map<String, Object> codeMap, OnTemplateListener listener);

}
