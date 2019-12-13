package template.widget;

import android.content.Context;
import android.widget.TextView;

import template.bean.ButtonTemplate;
import template.bean.TemplateValue;
import template.com.form.R;

public class ButtonTemplateView extends BaseTemplateView<ButtonTemplate>{
    private TextView hint;

    public ButtonTemplateView(Context context) {
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
        return BUTTON_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, ButtonTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, template, value);

    }
}
