package template.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import template.bean.Attr;
import template.bean.ButtonTemplate;

public class ButtonTemplateView extends BaseTemplateView<ButtonTemplate>{
    public ButtonTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return BUTTON_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, ButtonTemplate template, Object value, Attr attr) {
        holder.getConvertView().setClickable(attr.editable);
        super.initView(holder, template, value, attr);
        text.setVisibility(View.VISIBLE);
        editText.setVisibility(View.INVISIBLE);
        hint.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(template.hint)) {
            text.setHint(template.hint);
            hint.setText("");
        }else {
            text.setText("");
            hint.setText("请选择");
        }


//        if (value != null && !TextUtils.isEmpty(value.toString())) {
//            text.setText(value.toString());
//            hint.setText("");
//        } else {
//            text.setText("");
//            hint.setText("请选择");
//        }


    }
}
