package template.control;

import android.content.Context;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.ButtonTemplate;
import template.bean.TemplateValue;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
import template.widget.ButtonTemplateView;

@Template(tag = "button")
public class ButtonTemplateControl extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return ButtonTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        this.context = context;
        return new ButtonTemplateView(context);
    }

    @Override
    protected void onClickHolder(BaseTemplate template, TemplateValue value){
        if(commandListener != null)
            commandListener.onTemplateCommand(template.name, ((ButtonTemplate)template).command);
    }
}
