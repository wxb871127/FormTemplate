package template.control;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.wadata.customexpressionlib.ExpressionHelper;
import java.util.Map;
import base.util.TemplateList;
import template.bean.BaseTemplate;
import template.bean.TemplateValue;
import template.interfaces.OnTemplateCommandListener;
import template.interfaces.OnTemplateListener;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.tree.Node;

/**
 * 使用模板方法模式 控制表单的级联项目（包括是否显示、是否可编辑）
 * @param <T>
 */
public abstract class BaseTemplateControl<T extends BaseTemplate> {
    T template;
    protected Map<String, TemplateValue> valueMap;
    protected BaseTemplateDialog dialog;
    protected OnTemplateListener listener;
    protected Context context;
    protected OnTemplateCommandListener commandListener;

    public abstract Class<? extends BaseTemplate> getTemplateClass();
    public abstract BaseTemplateView getTemplateView(Context context);
    protected void onDialogDataChanged(BaseTemplate template, Object object, boolean notify){
        if(listener != null)
            listener.onDataChanged(template, object, notify);
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
           return ExpressionHelper.getExpressionUtil().getExpressionUtil().logicExpression(template.editable, valueMap, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isShow(Map<String, Object> valueMap){
        try {
            return ExpressionHelper.getExpressionUtil().getExpressionUtil().logicExpression(template.show, valueMap, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    protected Object getRealValue(Object value){
        return value;
    }

    public void initView(final Context context, final BaseViewHolder holder, Node node, final TemplateList templates, final T template,
                         final Map<String, TemplateValue> valueMap, Map<String, Object> codeMap, boolean editMode, Map<String, Boolean> manual) {
        this.valueMap = valueMap;
        TemplateValue templateValue = valueMap.get(template.name);
        if(templateValue == null)
            templateValue = new TemplateValue();
        final BaseTemplateView templateView = getTemplateView(context);
        boolean editable;
        if(!editMode)
            editable = editMode;
        else
            editable = isEditable(codeMap);
        templateValue.editable = editable;

        Object value = null;
        if(!TextUtils.isEmpty(template.value)) {////value表达式不为空 计算表达式值
            value = ExpressionHelper.getExpressionUtil().getExpressionUtil().executeExpression(template.value, codeMap);
            templateValue.value = getRealValue(value);
            codeMap.put(template.name, value);
        }else if(templateValue.value == null){
            if(!template.useinitValue && !TextUtils.isEmpty(template.initValue)){
                value = getRealValue(template.initValue);
                codeMap.put(template.name, value);
                template.useinitValue = true;
                templateValue.value = value;
            }
        }
        templateValue.showValue = template.getShowName(templateValue.value, context);

        Boolean exception = false;
        Boolean refuse = false;
        try {
            exception = valueMap.get(template.name).exception;
            refuse = valueMap.get(template.name).refuse;
            if(!manual.get(template.name)) {
                if(!TextUtils.isEmpty(template.exception))
                    exception = ExpressionHelper.getExpressionUtil().getExpressionUtil().logicExpression(template.exception, codeMap, false);
                valueMap.get(template.name).exception = exception;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        manual.put(template.name, false);
        templateValue.exception = exception;
        templateValue.refuse = refuse;

        holder.setShow(isShow(codeMap));
        templateView.setOnTemplateListener(listener);
        templateView.initView(holder, node, template, templateValue);

        if(isEditable(codeMap))
        holder.onClickContent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHolder(template, valueMap.get(template.name).value);
            }
        });
    }

    protected void onClickHolder(T template, Object value){
        dialog = getDialog(context, template);

        if(dialog != null) {
            dialog.initDialog(template, value);
            dialog.showDialog();
            dialog.setOnTemplateListener(new OnTemplateListener() {
                @Override
                public void onDataChanged(BaseTemplate key, Object value, boolean notify) {
                    onDialogDataChanged(key, value, notify);
                }

                @Override
                public void onAttrChanged(BaseTemplate key, String attr, Object value, boolean notify) {
                    if(listener != null)
                        listener.onAttrChanged(key, attr, value, notify);
                }

                @Override
                public void onDatasChanged(Map<String, Object> map, boolean notify) {

                }
            });
        }
    }

}
