package template.control;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.ButtonTemplate;
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
    protected void onClickHolder(BaseViewHolder holder) {
        Log.e("xxxxxxxxxxxxx", "button on click.........");
    }
}
