package template.widget;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import template.bean.BaseTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.interfaces.OnTemplateListener;

public abstract class BaseTemplateView<T extends BaseTemplate> extends RelativeLayout{
    protected Context mContext;
    protected TextView required;
    protected TextView label;
    protected TextView tvUnit;
    protected View vBox;
    protected View attrBox;
    protected EditText editText;
    protected TextView text;
    protected TextView hint;
    protected View refuse;//拒检
    protected ImageView refuseIcon;
    protected View exception;//异常
    protected ImageView exceptionIcon;
    protected T template;
    protected TemplateValue value;
    protected OnTemplateListener templateListener;
    protected BaseViewHolder holder;
    protected ImageView quote;

    protected static final int SECTION_TYPE = 0;
    protected static final int INPUT_TYPE = 1;
    protected static final int RADIO_TYPE = 2;
    protected static final int SELECT_TYPE = 3;
    protected static final int DATE_TYPE = 4;
    protected static final int SEARCH_TYPE = 5;
    protected static final int LIST_TYPE = 6;
    protected static final int BUTTON_TYPE = 7;

    public void setOnTemplateListener(OnTemplateListener listener){
        this.templateListener = listener;
    }

    public BaseTemplateView(Context context) {
        super(context);
        mContext = context;
    }

    public int getlayout() {
        return R.layout.common_template;
    }

    public abstract int getType();

    public void onFouces(){

    }

    /**
     *
     * @param holder    layout的view
     * @param template  view的xml配置属性
     * @param value     数据值
     */
    public void initView(final BaseViewHolder holder, final T template, final TemplateValue value){
        this.template = template;
        this.holder = holder;
        this.value = value;
        required = (TextView) holder.getViewById(R.id.template_required);
        label = (TextView)holder.getViewById(R.id.template_label);
        vBox = holder.getViewById(R.id.common_template_box);
        attrBox = holder.getViewById(R.id.template_attr);
        editText = (EditText) holder.getViewById(R.id.template_edit);
        text = (TextView)holder.getViewById(R.id.template_text);
        hint = (TextView) holder.getViewById(R.id.common_template_hint);
        tvUnit = (TextView)holder.getViewById(R.id.template_input_unit);
        refuse = holder.getViewById(R.id.template_refuse);
        exception = holder.getViewById(R.id.template_exception);
        refuseIcon = (ImageView)holder.getViewById(R.id.template_refuse_icon);
        exceptionIcon = (ImageView)holder.getViewById(R.id.template_exception_icon);
        quote = (ImageView) holder.getViewById(R.id.common_template_quote);
        if(refuse != null) {
            setException(value.exception);
            setRefuse(value.refuse);
            refuse.setEnabled(value.editable);
            refuse.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    value.refuse = !value.refuse;
                    if (templateListener != null) {
                        templateListener.onAttrChanged(template, "refuse", value.refuse, true);
                    }
                }
            });
        }
        if(exception != null) {
            exception.setEnabled(value.editable);
            exception.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    value.exception = !value.exception;
                    if(templateListener != null)
                        templateListener.onAttrChanged(template, "exception", value.exception, true);
                }
            });
        }

        label.setText(template.label);
        if("true".equals(template.required))
            required.setVisibility(VISIBLE);
        else
            required.setVisibility(GONE);
        if(tvUnit != null) {
            tvUnit.setText("");
            if (!TextUtils.isEmpty(template.unit)) {
                tvUnit.setVisibility(View.VISIBLE);
                tvUnit.setText(Html.fromHtml(template.unit));
            }
        }
        setEdit(value.editable);
    }

    protected void setEdit(boolean editable){
        if(vBox != null) {
            if (editable) {
                holder.getConvertView().setClickable(true);
                vBox.setBackgroundResource(R.drawable.bg_color_white_border);
                if(attrBox != null)
                    attrBox.setBackgroundResource(R.drawable.bg_color_white_border);
                if(text != null)
                    text.setTextColor(getResources().getColor(R.color.black));
            }else {
                holder.getConvertView().setClickable(false);
                vBox.setBackgroundResource(R.drawable.bg_color_gray_border);
                if(attrBox != null)
                    attrBox.setBackgroundResource(R.drawable.bg_color_gray_border);
                if(text != null)
                    text.setTextColor(getResources().getColor(R.color.B0));
            }
        }
    }

    protected void notifyItemViewData(Object object){
        if(templateListener != null)
            templateListener.onDataChanged(template, object, true);
    }

    public void setRefuse(boolean ret) {
        if (refuseIcon == null) return;
        if (ret) {
            refuseIcon.setBackground(getResources().getDrawable(R.drawable.radio_confim));
            setEdit(false);
        } else {
            refuseIcon.setBackground(getResources().getDrawable(R.drawable.radio_nomal));
            setEdit(true);
        }
    }

    public void setException(boolean ret){
        if(exceptionIcon == null) return;
        if(ret){
            exceptionIcon.setBackground(getResources().getDrawable(R.drawable.radio_confim));
        }else
            exceptionIcon.setBackground(getResources().getDrawable(R.drawable.radio_nomal));
    }
}
