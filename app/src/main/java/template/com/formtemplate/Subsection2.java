package template.com.formtemplate;

import android.content.Context;
import android.widget.TextView;

import java.util.Map;

import base.util.TemplateList;
import template.bean.CustomTemplate;
import template.config.CustomView;
import template.interfaces.OnTemplateListener;
import template.widget.BaseViewHolder;

public class Subsection2 extends CustomView {
    @Override
    public int getContentLayout() {
        return R.layout.xj2_layout;
    }

    @Override
    public void initView(Context context, BaseViewHolder holder, Map valueMap, TemplateList templates, CustomTemplate template, Map attrMap, OnTemplateListener listener) {
        TextView textView = (TextView) holder.getViewById(R.id.tx);
        textView.setText(valueMap.keySet().toString());
    }
}
