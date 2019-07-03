package template.widget;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import template.bean.BaseTemplate;
import template.com.form.R;

public abstract class BaseTemplateView<T extends BaseTemplate> extends RelativeLayout{
    protected Context mContext;
    protected TextView required;
    protected TextView label;
    protected TextView tvUnit;
    protected View vBox;
    protected EditText editText;
    protected TextView text;
    protected TextView hint;
    protected View refuse;//拒检
    protected ImageView refuseIcon;
    protected View exception;//异常
    protected ImageView exceptionIcon;
    protected T template;
    protected Object value;
    protected OnTemplateListener templateListener;
    private boolean isRefuse;

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

    /**
     *
     * @param holder    layout的view
     * @param template  view的xml配置属性
     * @param value     数据值
     */
    public void initView(final BaseViewHolder holder, final T template, final Object value, boolean editable){
        this.template = template;
        this.value = value;
        required = (TextView) holder.getViewById(R.id.template_required);
        label = (TextView)holder.getViewById(R.id.template_label);
        vBox = holder.getViewById(R.id.common_template_box);
        editText = (EditText) holder.getViewById(R.id.template_edit);
        text = (TextView)holder.getViewById(R.id.template_text);
        hint = (TextView) holder.getViewById(R.id.common_template_hint);
        tvUnit = (TextView)holder.getViewById(R.id.template_input_unit);
        refuse = holder.getViewById(R.id.template_refuse);
        exception = holder.getViewById(R.id.template_exception);
        refuseIcon = (ImageView)holder.getViewById(R.id.template_refuse_icon);
        exceptionIcon = (ImageView)holder.getViewById(R.id.template_exception_icon);

        if(refuse != null) {
            setException(template.isException);
            setRefuse(template.isRefuse);
            refuse.setClickable(true);
            refuse.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    template.isRefuse = !template.isRefuse;
                    setRefuse(template.isRefuse);
                    if(templateListener != null)
                        templateListener.onAttrClick(template, "refuse");
                }
            });
        }
        if(exception != null) {
            exception.setClickable(true);
            exception.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    template.isException = !template.isException;
                    setException(template.isException);
                    if(templateListener != null)
                        templateListener.onAttrClick(template, "exception");
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
        if(vBox != null) {
            if (editable) {
                vBox.setBackgroundResource(R.drawable.bg_color_white_border);
                if(text != null)
                    text.setTextColor(getResources().getColor(R.color.black));
            }else {
                vBox.setBackgroundResource(R.drawable.bg_color_gray_border);
                if(text != null)
                    text.setTextColor(getResources().getColor(R.color.B0));
            }
        }
    }

    protected void notifyItemViewData(Object object){
        if(templateListener != null)
            templateListener.onDataChange(template, object);
    }

    public void setRefuse(boolean ret){
        if(refuseIcon == null) return;
        if(ret){
            refuseIcon.setBackground(getResources().getDrawable(R.drawable.radio_confim));
        }else
            refuseIcon.setBackground(getResources().getDrawable(R.drawable.radio_nomal));
    }

    public void setException(boolean ret){
        if(exceptionIcon == null) return;
        if(ret){
            exceptionIcon.setBackground(getResources().getDrawable(R.drawable.radio_confim));
        }else
            exceptionIcon.setBackground(getResources().getDrawable(R.drawable.radio_nomal));
    }
}
