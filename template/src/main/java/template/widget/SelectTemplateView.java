package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import template.bean.Attr;
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
    public void initView(BaseViewHolder holder, SelectTemplate template, Object value, Attr attr) {
        holder.getConvertView().setClickable(attr.editable);
        super.initView(holder, template, value, attr);

        hint.setVisibility(VISIBLE);
        editText.setVisibility(View.GONE);
        if (value != null && !TextUtils.isEmpty(value.toString())) {
            text.setText(value.toString());
            hint.setText("");
        } else {
            text.setText("");
            hint.setText("请选择（可多选）");
        }

        String codes = template.getCodes(value);
        if(!TextUtils.isEmpty(codes))
            notifyItemViewData(codes);
    }
}
