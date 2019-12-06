package template.control;

import android.content.Context;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.InputTemplate;
import template.widget.BaseTemplateView;
import template.widget.InputTemplateView;

@Template(tag = "input")
public class InputTemplateControl extends BaseTemplateControl{

    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return InputTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        this.context = context;
        return new InputTemplateView(context);
    }

    @Override
    protected Object getRealValue(Object value) {
        if(value == null) return "";
        if(TextUtils.isEmpty(((InputTemplate)template).decimalFormat))
            return super.getRealValue(value);
        BigDecimal decimal = new BigDecimal(value.toString());
        return new DecimalFormat(((InputTemplate)template).decimalFormat).format(decimal);
    }
}
