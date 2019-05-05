package template.control;

import android.content.Context;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.SelectTemplate;
import template.widget.BaseTemplateView;
import template.widget.SelectTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.SelectTemplateDialog;

@Template(tag = "select")
public class SelectTemplateControl<T extends BaseTemplate> extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return SelectTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        return new SelectTemplateView(context);
    }

    @Override
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        SelectTemplateDialog selectTemplateDialog = new SelectTemplateDialog(context);
        return selectTemplateDialog;
    }
}
