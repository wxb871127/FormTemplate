package template.control;

import android.content.Context;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.SearchTemplate;
import template.widget.BaseTemplateView;
import template.widget.SearchTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.SearchTemplateDialog;

@Template(tag="search")
public class SearchTemplateControl<T extends BaseTemplate> extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return SearchTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        return new SearchTemplateView(context);
    }

    @Override
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        SearchTemplateDialog dialog = new SearchTemplateDialog(context);
        return dialog;
    }
}
