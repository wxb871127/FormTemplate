package template.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import template.bean.BaseTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.interfaces.OnTemplateListener;
import template.widget.tree.Node;

public abstract class BaseTemplateView<T extends BaseTemplate> extends RelativeLayout {
    protected Context mContext;
    protected TextView required;
    protected TextView label;
    protected LinearLayout layout;
    protected RelativeLayout title;
    protected LinearLayout viewContant;
    protected LinearLayout spinner;
    protected LinearLayout attr;
    protected View refuse;//拒检
    protected ImageView refuseIcon;
    protected TextView refuseText;
    protected View exception;//异常
    protected ImageView exceptionIcon;
    protected TextView exceptionText;
    protected T template;
    protected TemplateValue value;
    protected OnTemplateListener templateListener;
    protected BaseViewHolder holder;
    protected Node node;

    public void setOnTemplateListener(OnTemplateListener listener) {
        this.templateListener = listener;
    }

    public BaseTemplateView(Context context) {
        super(context);
        mContext = context;
    }

    public int getlayout() {
        return R.layout.template_common;
    }

    public int getContentLayout() {
        return 0;
    }

    protected int getSpinnerLayout() {
        return 0;
    }

    protected void initContentView() {

    }

    public abstract int getType();

    public void onFouces() {

    }

    /**
     * @param holder   layout的view
     * @param template view的xml配置属性
     * @param value    数据值
     */
    public void initView(final BaseViewHolder holder, Node node, final T template, final TemplateValue value) {
        this.template = template;
        this.holder = holder;
        this.value = value;
        this.node = node;
        layout = (LinearLayout) holder.getViewById(R.id.template_linelayout);
        required = (TextView) holder.getViewById(R.id.template_required);
        label = (TextView) holder.getViewById(R.id.template_label);
        title = (RelativeLayout) holder.getViewById(R.id.template_title);
        viewContant = (LinearLayout) holder.getViewById(R.id.common_template_box);
        spinner = (LinearLayout) holder.getViewById(R.id.template_spinner);
        refuse = holder.getViewById(R.id.template_refuse);
        exception = holder.getViewById(R.id.template_exception);
        refuseIcon = (ImageView) holder.getViewById(R.id.template_refuse_icon);
        exceptionIcon = (ImageView) holder.getViewById(R.id.template_exception_icon);
        refuseText = (TextView) holder.getViewById(R.id.text_refuse);
        exceptionText = (TextView) holder.getViewById(R.id.text_exception);
        attr = (LinearLayout) holder.getViewById(R.id.attr);

        if (viewContant != null && getContentLayout() != 0) {
            viewContant.removeAllViews();
            LayoutInflater.from(mContext).inflate(getContentLayout(), viewContant);
        }
        if (spinner != null) {
            if (getSpinnerLayout() != 0) {
                spinner.setVisibility(VISIBLE);
                spinner.removeAllViews();
                LayoutInflater.from(mContext).inflate(getSpinnerLayout(), spinner);
            } else
                spinner.setVisibility(GONE);
        }
        initContentView();

        if (!value.editable) {
            setValueEdit(value.editable);
            setExceptionEdit(value.editable);
            setRefuseEdit(value.editable);
        } else {
            if (value.refuse) {
                setValueEdit(false);
                setExceptionEdit(false);
                setRefuseEdit(true);
            } else {
                setValueEdit(value.editable);
                setExceptionEdit(value.editable);
                setRefuseEdit(value.editable);
            }
        }

        if (refuse != null) {
            setException(value.exception);
            setRefuse(value.refuse);
            refuse.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    value.refuse = !value.refuse;
                    if (templateListener != null) {
                        templateListener.onDataChanged(template, "", false);
                        templateListener.onAttrChanged(template, "exception", false, true);
                    }
                }
            });
        }
        if (exception != null) {
            if (template.hasExceptionExpression())
                exception.setEnabled(false);
            else
                exception.setEnabled(value.editable && !value.refuse);
            exception.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (templateListener != null)
                        templateListener.onAttrChanged(template, "exception", !value.exception, true);
                }
            });
        }

        if (label != null)
            label.setText(template.label);
        if (required != null) {
            if ("true".equals(template.required))
                required.setVisibility(VISIBLE);
            else
                required.setVisibility(GONE);
        }
    }

    protected void setValueEdit(boolean editable) {
        if (viewContant == null) return;
        setEditableTextColor(label, editable, R.color.template_c333333);
        viewContant.setEnabled(editable);
//        if (editable) {
//            title.setBackgroundColor(getResources().getColor(R.color.white));
//            viewContant.setBackgroundColor(getResources().getColor(R.color.white));
//        } else {
//            title.setBackgroundColor(getResources().getColor(R.color.Pad_Background));
//            viewContant.setBackgroundColor(getResources().getColor(R.color.Pad_Background));
//        }
    }

    protected void notifyItemViewData(Object object) {
        if (templateListener != null)
            templateListener.onDataChanged(template, object, true);
    }


    public void setRefuse(boolean ret) {
        if (refuseIcon == null) return;
        refuseIcon.setSelected(ret);
    }

    protected void setRefuseEdit(boolean editable) {
        if (refuse == null) return;
        setEditableTextColor(refuseText, editable, R.color.template_c666666);
        refuse.setEnabled(editable);
        int mResid = editable ? R.drawable.template_refuse_icon_selector : R.drawable.template_non_editable_icon_selector;
        refuseIcon.setBackgroundResource(mResid);
//        if (editable)
//            refuse.setBackgroundResource(R.color.white);
//        else
//            refuse.setBackgroundResource(R.color.Pad_Background);
    }

    protected void setExceptionEdit(boolean editable) {
        if (exception == null) return;
        setEditableTextColor(exceptionText, editable, R.color.template_c666666);
        exception.setEnabled(editable);
        int mResid = editable ? R.drawable.template_abnormal_icon_selector : R.drawable.template_non_editable_icon_selector;
        exceptionIcon.setBackgroundResource(mResid);
//        if (editable)
//            exception.setBackgroundResource(R.color.white);
//        else
//            exception.setBackgroundResource(R.color.Pad_Background);
    }

    public void setException(boolean ret) {
        if (exceptionIcon == null) return;
        exceptionIcon.setSelected(ret);
    }

    protected void setEditableTextColor(TextView textView, boolean editable) {
        setEditableTextColor(textView, editable, R.color.template_c333333);
    }

    protected void setEditableTextColor(TextView textView, boolean editable, int editColor) {
        if (editable)
            textView.setTextColor(ContextCompat.getColor(mContext, editColor));
        else
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.template_cbbbbbb));
    }
}
