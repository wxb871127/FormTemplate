package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import template.bean.RadioTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.widget.tree.Node;

public class RadioTemplateView extends BaseTemplateView<RadioTemplate>{
    private TextView hint;
    public RadioTemplateView(Context context) {
        super(context);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.template_select_content;
    }

    @Override
    protected void initContentView() {
        hint = (TextView) holder.getViewById(R.id.common_template_hint);
    }

    @Override
    public int getType() {
        return RADIO_TYPE;
    }

    @Override
    public void initView(final BaseViewHolder holder, Node node, RadioTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, node, template, value);
        if(value != null && !TextUtils.isEmpty(value.showValue)) {
            hint.setText(value.showValue);
            hint.setTextColor(getResources().getColor(R.color.black));
        }else{
            hint.setText("请选择");
            hint.setTextColor(getResources().getColor(R.color.c9));
        }
    }
}
