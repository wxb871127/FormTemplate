package template.widget;

import android.content.Context;
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

public abstract class BaseTemplateView<T extends BaseTemplate> extends RelativeLayout{
    protected Context mContext;
    protected TextView required;
    protected TextView label;
    protected LinearLayout layout;
    protected LinearLayout title;
    protected LinearLayout viewContant;
    protected LinearLayout spinner;
    protected LinearLayout attr;
    protected View refuse;//拒检
    protected ImageView refuseIcon;
    protected View exception;//异常
    protected ImageView exceptionIcon;
    protected T template;
    protected TemplateValue value;
    protected OnTemplateListener templateListener;
    protected BaseViewHolder holder;
    protected Node node;

    protected static final int SECTION_TYPE = 0;
    protected static final int INPUT_TYPE = 1;
    protected static final int RADIO_TYPE = 2;
    protected static final int SELECT_TYPE = 3;
    protected static final int DATE_TYPE = 4;
    protected static final int SEARCH_TYPE = 5;
    protected static final int LIST_TYPE = 6;
    protected static final int BUTTON_TYPE = 7;
    protected static final int CUSTOM_TYPE = 8;

    public void setOnTemplateListener(OnTemplateListener listener){
        this.templateListener = listener;
    }

    public BaseTemplateView(Context context) {
        super(context);
        mContext = context;
    }

    public int getlayout() {
        return R.layout.template_common;
    }

    protected int getContentLayout(){
        return 0;
    }

    protected int getSpinnerLayout(){
        return 0;
    }

    protected void initContentView(){

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
    public void initView(final BaseViewHolder holder, Node node, final T template, final TemplateValue value){
        this.template = template;
        this.holder = holder;
        this.value = value;
        this.node = node;
        layout = (LinearLayout) holder.getViewById(R.id.template_linelayout);
        required = (TextView) holder.getViewById(R.id.template_required);
        label = (TextView)holder.getViewById(R.id.template_label);
        title = (LinearLayout)holder.getViewById(R.id.template_title);
        viewContant = (LinearLayout)holder.getViewById(R.id.common_template_box);
        spinner = (LinearLayout)holder.getViewById(R.id.template_spinner);
        refuse = holder.getViewById(R.id.template_refuse);
        exception = holder.getViewById(R.id.template_exception);
        refuseIcon = (ImageView)holder.getViewById(R.id.template_refuse_icon);
        exceptionIcon = (ImageView)holder.getViewById(R.id.template_exception_icon);
        attr = (LinearLayout) holder.getViewById(R.id.attr);

        if(viewContant != null && getContentLayout() != 0) {
            viewContant.removeAllViews();
            LayoutInflater.from(mContext).inflate(getContentLayout(), viewContant);
        }
        if(spinner != null) {
            if (getSpinnerLayout() != 0) {
                spinner.setVisibility(VISIBLE);
                spinner.removeAllViews();
                LayoutInflater.from(mContext).inflate(getSpinnerLayout(), spinner);
            }else
                spinner.setVisibility(GONE);
        }
        initContentView();

        if(value.refuse){
            setValueEdit(false);
            setExceptionEdit(false);
        }else {
            setValueEdit(value.editable);
            setExceptionEdit(value.editable);
            setRefuseEdit(value.editable);
        }

        if(refuse != null) {
            setException(value.exception);
            setRefuse(value.refuse);
            refuse.setEnabled(value.editable);
            refuse.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    value.refuse = !value.refuse;
                    setRefuse(value.refuse);
                    setValueEdit(!value.refuse);
                    setExceptionEdit(!value.refuse);
                }
            });
        }
        if(exception != null) {
            exception.setEnabled(value.editable);
            exception.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(templateListener != null)
                        templateListener.onAttrChanged(template, "exception", !value.exception, true);
                }
            });
        }

        label.setText(template.label);
        if("true".equals(template.required))
            required.setVisibility(VISIBLE);
        else
            required.setVisibility(GONE);
    }

    protected void setValueEdit(boolean editable){
        if(viewContant == null) return;
        if (editable) {
            title.setBackgroundColor(getResources().getColor(R.color.white));
            viewContant.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            title.setBackgroundColor(getResources().getColor(R.color.Pad_Background));
            viewContant.setBackgroundColor(getResources().getColor(R.color.Pad_Background));
        }
        viewContant.setClickable(editable);
    }

    protected void notifyItemViewData(Object object){
        if(templateListener != null)
            templateListener.onDataChanged(template, object, true);
    }


    public void setRefuse(boolean ret) {
        if (refuseIcon == null) return;
        if (ret) {
            refuseIcon.setBackground(getResources().getDrawable(R.drawable.template_radio_confim));
        } else {
            refuseIcon.setBackground(getResources().getDrawable(R.drawable.template_radio_nomal));
        }
    }

    protected void setRefuseEdit(boolean editable){
        if (editable)
            refuse.setBackgroundResource(R.color.white);
        else
            refuse.setBackgroundResource(R.color.Pad_Background);
        refuse.setClickable(editable);
    }

    protected void setExceptionEdit(boolean editable){
        if (editable)
            exception.setBackgroundResource(R.color.white);
        else
            exception.setBackgroundResource(R.color.Pad_Background);
        exception.setClickable(editable);
    }

    public void setException(boolean ret){
        if(exceptionIcon == null) return;
        if(ret){
            exceptionIcon.setBackground(getResources().getDrawable(R.drawable.template_radio_confim));
        }else
            exceptionIcon.setBackground(getResources().getDrawable(R.drawable.template_radio_nomal));
    }
}
