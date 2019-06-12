package template.control;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import base.annotation.Template;
import base.util.ExpressionUtil;
import template.bean.BaseTemplate;
import template.interfaces.OnTemplateCommandListener;
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
//    protected BaseTemplateView view;
    protected BaseTemplateDialog dialog;
    protected OnTemplateListener listener;
    protected Context context;
    protected OnTemplateCommandListener commandListener;

    public abstract Class<? extends BaseTemplate> getTemplateClass();
    public abstract BaseTemplateView getTemplateView(Context context);

    public interface OnTemplateListener{
        void onTemplateUpdate(BaseTemplate key, Object value);//更新数据
    }

    public void setTemplateListener(OnTemplateListener listener){
        this.listener = listener;
    }

    public void setCommandListener(OnTemplateCommandListener listener){
        this.commandListener = listener;
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
        final BaseTemplateView templateView = getTemplateView(context);
        boolean editable;
        if(!editMode)
            editable = editMode;
        else editable = isEditable(valueMap);

        String showName = "";
        Object value = valueMap.get(template.name);

        ////初始化赋值
        if(!TextUtils.isEmpty(template.initValue)
                && (TextUtils.isEmpty(value.toString()))) {
            showName = template.getShowName(valueMap.get(template.name), context);
            templateView.initView(holder, template, showName, editable);
        }

        if(!TextUtils.isEmpty(template.value)){
            ////根据value表达式计算值
            Object object = ExpressionUtil.getExpressionUtil().executeExpression(template.value, valueMap);
            if(object != null) {
                showName = object.toString();
                templateView.initView(holder, template, showName, editable);
                valueMap.put(template.name, object);
                verifyData(template, object, valueMap,templateView, holder);
            }
        }else {
            showName = template.getShowName(valueMap.get(template.name), context);
            templateView.initView(holder, template, showName, editable);
        }
//        templateView.initView(holder, template, showName, editable);

        holder.setShow(isShow(valueMap));
        templateView.setOnTemplateListener(new template.widget.OnTemplateListener() {
            @Override
            public void onDataChange(BaseTemplate template1,Object object) {
                valueMap.put(template1.name, object);
                verifyData(template1, object, valueMap, templateView,holder);
            }
        });

        if(isEditable(valueMap))
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHolder(templateView, holder, template);
            }
        });
    }

    protected void onClickHolder(final BaseTemplateView templateView ,final BaseViewHolder holder, T template){
        dialog = getDialog(context, template);
        if(dialog != null) {
            dialog.initDialog(template, valueMap.get(template.name));
            dialog.showDialog();
            dialog.setOnTemplateListener(new template.widget.OnTemplateListener() {
                @Override
                public void onDataChange(BaseTemplate template, Object object) {
                    verifyData(template, object, valueMap, templateView, holder);
                }
            });
        }
    }

    protected void verifyData(BaseTemplate template, Object object, final Map<String, Object> valueMap, final BaseTemplateView templateView, BaseViewHolder holder){
        if(TextUtils.isEmpty(template.exception)){
            if(listener != null)
                listener.onTemplateUpdate(template, object);
            return;
        }

        if(object instanceof BigDecimal){
            valueMap.put (template.name, ((BigDecimal) object).doubleValue());
        }else
            valueMap.put(template.name, object);
        try {
            Boolean ret = ExpressionUtil.getExpressionUtil().logicExpression(template.exception, valueMap, false);
            if(ret){
                handleException(template, object, templateView, holder);
            }else{
                template.isException = false;
                templateView.setException(false);
            }

            if(listener != null)
                listener.onTemplateUpdate(template, object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleException(BaseTemplate template, Object object, final BaseTemplateView templateView, BaseViewHolder holder)  {
        template.isException = true;
        templateView.setException(true);
    }
}
