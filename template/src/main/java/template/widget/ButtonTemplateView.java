package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import template.bean.ButtonTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.config.TemplateConfig;
import template.widget.tree.Node;

public class ButtonTemplateView extends BaseTemplateView<ButtonTemplate>{
    private TextView hint;

    public ButtonTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getContentLayout() {
        return R.layout.template_select_content;
    }

    @Override
    protected void initContentView() {
        hint = (TextView) holder.getViewById(R.id.common_template_hint);
    }

    @Override
    public int getType() {
        return TemplateConfig.BUTTON_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, Node node, ButtonTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, node, template, value);
        if (value != null && !TextUtils.isEmpty(value.showValue)) {
            hint.setText(value.showValue);
            //hint.setTextColor(getResources().getColor(R.color.black));
        } else {
            hint.setText("请选择");
            //hint.setTextColor(getResources().getColor(R.color.c9));
        }
    }

    @Override
    protected void setValueEdit(boolean editable) {
        super.setValueEdit(editable);
        if (value != null && !TextUtils.isEmpty(value.showValue)) {
            setEditableTextColor(hint,editable,R.color.black);
        }else{
            setEditableTextColor(hint,editable,R.color.c9);
        }
        viewContant.setEnabled(true);
    }
}
