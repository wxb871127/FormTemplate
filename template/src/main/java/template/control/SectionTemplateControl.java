package template.control;

import android.content.Context;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.SectionTemplate;
import template.widget.BaseTemplateView;
import template.widget.SectionTemplateView;

@Template(tag="section")
public class SectionTemplateControl extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return SectionTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        return new SectionTemplateView(context);
    }


}
