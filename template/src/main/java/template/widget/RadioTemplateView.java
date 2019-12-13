package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import template.bean.RadioTemplate;
import template.bean.TemplateValue;
import template.com.form.R;

public class RadioTemplateView extends BaseTemplateView<RadioTemplate>{
    private TextView hint;
    public RadioTemplateView(Context context) {
        super(context);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.select_template_content;
    }

    @Override
    protected void initContentView() {
        hint = (TextView) holder.getViewById(R.id.common_template_hint);
    }

    @Override
    public int getType() {
        return RADIO_TYPE;
    }

    @Override
    public void initView(final BaseViewHolder holder, RadioTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, template, value);
        if(!TextUtils.isEmpty(value.showValue))
            hint.setText(value.showValue);
        else
            hint.setText("请选择");
    }
}
