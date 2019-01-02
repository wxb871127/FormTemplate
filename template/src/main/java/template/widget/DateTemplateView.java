package template.widget;

import android.content.Context;
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
        holder.getConvertView().setClickable(true);
        super.initView(holder, template, value, editable);
        hint.setVisibility(VISIBLE);
        editText.setText(value);
    }
}
