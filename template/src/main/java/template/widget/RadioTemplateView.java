package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import template.bean.RadioTemplate;
import template.bean.TemplateValue;

public class RadioTemplateView extends BaseTemplateView<RadioTemplate>{
    public RadioTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return RADIO_TYPE;
    }

    @Override
    public void initView(final BaseViewHolder holder, RadioTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, template, value);
        hint.setVisibility(VISIBLE);
        editText.setVisibility(View.GONE);
        if (value != null && !TextUtils.isEmpty(value.toString())) {
            text.setText(value.showValue);
            hint.setText("");
        } else {
            text.setText("");
            hint.setText("请选择");
        }
//        if (value != null && !TextUtils.isEmpty(value.toString())) {
//            String code = template.getCode(value.toString());
//            notifyItemViewData(code);
//        }
    }
}
