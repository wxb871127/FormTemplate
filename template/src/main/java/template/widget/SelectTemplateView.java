package template.widget;

import android.content.Context;
import android.text.TextUtils;

import template.bean.SelectTemplate;

public class SelectTemplateView extends BaseTemplateView<SelectTemplate>{
    public SelectTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void initView(BaseViewHolder holder, SelectTemplate template, String value, boolean editable) {
        holder.getConvertView().setClickable(editable);
        super.initView(holder, template, value, editable);
        hint.setText("请选择（可多选）");
        hint.setVisibility(VISIBLE);
        editText.setSingleLine(true);
        editText.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        editText.setText(value);
    }
}
