package template.control;

import android.content.Context;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.RadioTemplate;
import template.widget.BaseTemplateView;
import template.widget.RadioTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.RadioTemplateDialog;

@Template(tag = "radio")
public class RadioTemplateControl extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return RadioTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(final Context context) {
        RadioTemplateView radioTemplateView = new RadioTemplateView(context);
        return radioTemplateView;
    }

    @Override
    public BaseTemplateDialog getDialog(Context context) {
        RadioTemplateDialog dialog = new RadioTemplateDialog(context);
        return dialog;
    }
}
