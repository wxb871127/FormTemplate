package template.control;

import android.content.Context;

import java.util.Map;

import base.annotation.Template;
import base.util.TemplateList;
import template.bean.BaseTemplate;
import template.bean.CustomTemplate;
import template.config.TemplateConfig;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
import template.widget.CustomTemplateView;

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
    public void initView(Context context, BaseViewHolder holder, final TemplateList templates, final CustomTemplate template, Map valueMap, Map codeMap, boolean editMode, Map manual) {
        super.initView(context, holder, templates, template, valueMap, codeMap, editMode, manual);
        TemplateConfig.getCustomView(Integer.parseInt(template.command)).initView(context, holder, valueMap, templates, template, codeMap, listener);
    }
}
