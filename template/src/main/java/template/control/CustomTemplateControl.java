package template.control;

import android.content.Context;

import java.util.Map;

import base.annotation.Template;
import base.util.TemplateList;
import template.bean.BaseTemplate;
import template.bean.CustomTemplate;
import template.bean.TemplateValue;
import template.config.TemplateConfig;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
import template.widget.CustomTemplateView;
import template.widget.tree.Node;

@Template(tag = "custom")
public class CustomTemplateControl extends BaseTemplateControl<CustomTemplate>{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return CustomTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        return new CustomTemplateView(context);
    }

    @Override
    protected void onClickHolder(CustomTemplate template, TemplateValue templateValue) {
        if(!templateValue.editable) return;
        super.onClickHolder(template, templateValue);
    }

    @Override
    public void initView(Context context, BaseViewHolder holder, Node node, final TemplateList templates, final CustomTemplate template, Map valueMap, Map codeMap, boolean editMode, Map manual) {
        super.initView(context, holder, node, templates, template, valueMap, codeMap, editMode, manual);
        TemplateConfig.getCustomView(Integer.parseInt(template.command)).initView(context, holder, valueMap, templates, template, codeMap, listener);
    }
}
