package template.control;

import android.content.Context;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.DateTemplate;
import template.widget.BaseTemplateView;
import template.widget.DateTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.DateTemplateDialog;

@Template(tag = "date")
public class DateTemplateControl<T extends BaseTemplate> extends BaseTemplateControl{


    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return DateTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        DateTemplateView dateTemplateView = new DateTemplateView(context);
        return dateTemplateView;
    }

    @Override
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        DateTemplateDialog dialog = new DateTemplateDialog(context);
        return dialog;
    }
}
