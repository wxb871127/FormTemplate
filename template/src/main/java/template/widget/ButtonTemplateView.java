package template.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import template.bean.ButtonTemplate;

public class ButtonTemplateView extends BaseTemplateView<ButtonTemplate>{
    public ButtonTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return 7;
    }

    @Override
    public void initView(BaseViewHolder holder, ButtonTemplate template, Object value, boolean editable) {
        holder.getConvertView().setClickable(editable);
        super.initView(holder, template, value, editable);
        text.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        hint.setVisibility(VISIBLE);
        if (value != null && !TextUtils.isEmpty(value.toString())) {
            text.setText(value.toString());
            hint.setText("");
        } else {
            text.setText("");
            hint.setText("请选择");
        }


    }
}
