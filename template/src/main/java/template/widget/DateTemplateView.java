package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import template.bean.DateTemplate;

public class DateTemplateView extends BaseTemplateView<DateTemplate>{

    public DateTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public  void initView(BaseViewHolder holder, DateTemplate template, String value, boolean editable) {
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
