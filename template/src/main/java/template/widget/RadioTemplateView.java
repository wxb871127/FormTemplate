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
    public void initView(final BaseViewHolder holder, RadioTemplate template, Object value, boolean editable) {
        holder.getConvertView().setClickable(editable);
        super.initView(holder, template, value, editable);
        hint.setVisibility(VISIBLE);
        editText.setVisibility(View.GONE);
        if (value != null && !TextUtils.isEmpty(value.toString())) {
            text.setText(value.toString());
            hint.setText("");
        } else {
            text.setText("");
            hint.setText("请选择");
        }
        if (value != null && !TextUtils.isEmpty(value.toString())) {
            String code = template.getCode(value.toString());
            notifyItemViewData(code);
        }
    }
}
