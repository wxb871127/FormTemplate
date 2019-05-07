package template.control;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import base.annotation.Template;
import base.util.ExpressionUtil;
import template.bean.BaseTemplate;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
import template.widget.dialog.BaseTemplateDialog;

/**
 * 使用模板方法模式 控制表单的级联项目（包括是否显示、是否可编辑）
 * @param <T>
 */
public abstract class BaseTemplateControl<T extends BaseTemplate> {
    T template;
    protected Map<String, Object> valueMap;
    protected BaseTemplateView view;
    protected BaseTemplateDialog dialog;
    protected OnTemplateListener listener;

    public abstract Class<? extends BaseTemplate> getTemplateClass();
    public abstract BaseTemplateView getTemplateView(Context context);

    public interface OnTemplateListener{
        public void onTemplateUpdate(BaseTemplate key, Object value);
    }

    public void setTemplateListener(OnTemplateListener listener){
        this.listener = listener;
    }

    public void setTemplate(T template){
        this.template = template;
    }

    public  BaseTemplate getTemplate(){
        return template;
    }

    public BaseTemplateDialog getDialog(Context context, T template){
        return null;
    }

    public boolean isEditable(Map<String, Object> valueMap){
        try {
           return ExpressionUtil.getExpressionUtil().logicExpression(template.editable, valueMap, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isShow(Map<String, Object> valueMap){
        try {
            return ExpressionUtil.getExpressionUtil().logicExpression(template.show, valueMap, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void initView(final Context context, final BaseViewHolder holder, final T template,
                         final Map<String, Object> valueMap, boolean editMode){
        this.valueMap = valueMap;
        view = getTemplateView(context);
        boolean editable;
        if(!editMode)
            editable = editMode;
        else editable = isEditable(valueMap);

        String showName = "";
        Object value = valueMap.get(template.name);

        ////初始化赋值
        if(template.initValue!= null && !TextUtils.isEmpty(template.initValue)
                && (value == null || TextUtils.isEmpty(value.toString()))) {
            showName = template.getShowName(valueMap.get(template.name), context);
        }

        if(template.value != null && !TextUtils.isEmpty(template.value)){
            ////根据value表达式计算值
            Object object = ExpressionUtil.getExpressionUtil().executeExpression(template.value, valueMap);
            if(object != null) {
                showName = object.toString();
                valueMap.put(template.name, object);
            }
        }else
            showName = template.getShowName(valueMap.get(template.name), context);

        view.initView(holder, template, showName, editable);

        holder.setShow(isShow(valueMap));
        view.setOnTemplateListener(new template.widget.OnTemplateListener() {
            @Override
            public void onDataChange(BaseTemplate template1,Object object) {
                verifyData(template1, object, holder);
            }
        });

        if(isEditable(valueMap))
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = getDialog(context, template);
                if(dialog != null) {
                    dialog.initDialog(template, valueMap.get(template.name));
                    dialog.showDialog();
                    dialog.setOnTemplateListener(new template.widget.OnTemplateListener() {
                        @Override
                        public void onDataChange(BaseTemplate name, Object object) {
                            if (listener != null)
                                listener.onTemplateUpdate(template, object);
                        }
                    });
                }
            }
        });
    }

    protected void verifyData(BaseTemplate name, Object object,BaseViewHolder holder){
        if(listener != null)
            listener.onTemplateUpdate(name, object);
    }

}
