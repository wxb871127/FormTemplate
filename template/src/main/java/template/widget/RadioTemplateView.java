package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import template.bean.RadioTemplate;

public class RadioTemplateView extends BaseTemplateView<RadioTemplate>{
    public RadioTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void initView(final BaseViewHolder holder, RadioTemplate template, String value, boolean editable) {
        holder.getConvertView().setClickable(editable);
        super.initView(holder, template, value, editable);
        hint.setVisibility(VISIBLE);
        editText.setVisibility(View.GONE);
        if (value != null && !TextUtils.isEmpty(value)) {
            text.setText(value);
            hint.setText("");
        } else {
            text.setText("");
            hint.setText("请选择");
        }
    }
}
