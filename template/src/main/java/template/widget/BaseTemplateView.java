package template.widget;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    protected T template;
    protected Object value;
    protected OnViewListener listener;
    protected OnTemplateListener templateListener;

    //定义View的事件接口
    public interface OnViewListener{
        public void onViewClick();
    }

    public void setOnViewListener(OnViewListener listener){
        this.listener = listener;
    }

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
    public void initView(final BaseViewHolder holder, final T template, final String value, boolean editable){
        this.template = template;
        this.value = value;
        required = (TextView) holder.getViewById(R.id.template_required);
        label = (TextView)holder.getViewById(R.id.template_label);
        vBox = holder.getViewById(R.id.common_template_box);
        editText = (EditText) holder.getViewById(R.id.template_edit);
        text = (TextView)holder.getViewById(R.id.template_text);
        hint = (TextView) holder.getViewById(R.id.common_template_hint);
        tvUnit = (TextView)holder.getViewById(R.id.template_input_unit);


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

        if(holder.getConvertView().isClickable())
        holder.getConvertView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onViewClick();
            }
        });
    }

    public void setShow(BaseViewHolder holder,boolean show){
        ViewGroup.LayoutParams layoutParams = holder.getConvertView().getLayoutParams();
        holder.getConvertView().setLayoutParams(layoutParams);
        if(!show){
            layoutParams.height = 0;
            layoutParams.width = 0;
            holder.getConvertView().setVisibility(GONE);
        }else {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.getConvertView().setVisibility(VISIBLE);
        }
    }

//    public void setEdit(boolean edit) {
//        if (edit) {
//            if(vBox != null)
//                vBox.setBackgroundResource(R.drawable.bg_color_white_border);
//            if(editText!= null)
//            editText.setClickable(true);
//        } else {
//            if(vBox != null)
//                vBox.setBackgroundResource(R.drawable.bg_color_gray_border);
//            if(editText!= null)
//            editText.setClickable(false);
//        }
//    }


}
