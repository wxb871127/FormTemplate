package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import template.bean.SelectTemplate;
import template.bean.TemplateValue;
import template.com.form.R;

public class SelectTemplateView extends BaseTemplateView<SelectTemplate>{
    private TextView hint;
    public SelectTemplateView(Context context) {
        super(context);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.select_template_content;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        hint = (TextView) holder.getViewById(R.id.common_template_hint);
    }

    @Override
    public int getType() {
        return SELECT_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, SelectTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, template, value);
        if(!TextUtils.isEmpty(value.showValue))
            hint.setText(value.showValue);
        else
            hint.setText("请选择（可多选）");
    }
}
