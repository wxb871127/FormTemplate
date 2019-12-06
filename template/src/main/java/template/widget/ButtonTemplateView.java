package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import template.bean.ButtonTemplate;
import template.bean.TemplateValue;

public class ButtonTemplateView extends BaseTemplateView<ButtonTemplate>{
    public ButtonTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return BUTTON_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, ButtonTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, template, value);
        text.setVisibility(View.VISIBLE);
        editText.setVisibility(View.INVISIBLE);
        hint.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(value.showValue)) {
            text.setHint(value.showValue);
            hint.setText("");
        }else {
            text.setText("");
            hint.setText("请选择");
        }
    }
}
