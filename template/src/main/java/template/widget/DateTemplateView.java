package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import template.bean.DateTemplate;
import template.bean.TemplateValue;

public class DateTemplateView extends BaseTemplateView<DateTemplate>{

    public DateTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return DATE_TYPE;
    }

    @Override
    public  void initView(BaseViewHolder holder, DateTemplate template, TemplateValue value) {
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
    }
}
