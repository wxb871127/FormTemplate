package template.control;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import org.json.JSONObject;
import java.util.Map;
import base.util.ExpressionUtil;
import base.util.TemplateList;
import template.bean.Attr;
import template.bean.BaseTemplate;
import template.interfaces.OnTemplateCommandListener;
import template.interfaces.OnTemplateListener;
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
    protected BaseTemplateDialog dialog;
    protected OnTemplateListener listener;
    protected Context context;
    protected OnTemplateCommandListener commandListener;

    public abstract Class<? extends BaseTemplate> getTemplateClass();
    public abstract BaseTemplateView getTemplateView(Context context);
    protected void onDialogDataChanged(BaseTemplate template, Object object){
        if(listener != null)
            listener.onDataChanged(template, object);
    }

//    public interface OnTemplateListener{
//        void onDataChanged(BaseTemplate key, Object value);//数据发生改变
//        void onAttrChanged(BaseTemplate key, String attr, Object value);//属性发生改变
//        void onDatasChanged(Map<String, Object> map);//多个数据发生改变
//    }

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

    public void initView(final Context context, final BaseViewHolder holder, final TemplateList templates, final T template,
                         final Map<String, Object> valueMap, final Map<String, Object> attrMap, boolean editMode, Map<String, Boolean> manual) {
        this.valueMap = valueMap;
        final BaseTemplateView templateView = getTemplateView(context);
        boolean editable;
        if(!editMode)
            editable = editMode;
        else editable = isEditable(valueMap);

        String showName = "";
        if(!TextUtils.isEmpty(template.value)){////value表达式不为空 计算表达式值
            Object object = ExpressionUtil.getExpressionUtil().executeExpression(template.value, valueMap);
            if(object != null) {
                showName = object.toString();
                valueMap.put(template.name, object);//计算完值立即存储数据，便于其他字段计算
            }
        }else {//当前值为空则赋值初始值 当前值不为空使用当前值
            showName = template.getShowName(valueMap.get(template.name), context);
            if(TextUtils.isEmpty(showName) && !TextUtils.isEmpty(template.initValue))
                showName = template.getShowName(template.initValue, context);
        }

        Boolean exception = false;
        Boolean refuse = false;
        try {
            exception = ((JSONObject)attrMap.get(template.name)).optBoolean("exception");
            refuse =  ((JSONObject)attrMap.get(template.name)).optBoolean("refuse");
            if(!manual.get(template.name)) {
                if(!TextUtils.isEmpty(template.exception))
                    exception = ExpressionUtil.getExpressionUtil().logicExpression(template.exception, valueMap, false);
                ((JSONObject)attrMap.get(template.name)).put("exception", exception);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        manual.put(template.name, false);

        holder.setShow(isShow(valueMap));
        templateView.setOnTemplateListener(listener);
        templateView.initView(holder, template, showName, new Attr(refuse, exception, editable));

        if(isEditable(valueMap))
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHolder(template, valueMap.get(template.name));
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
                public void onDataChanged(BaseTemplate key, Object value) {
                    onDialogDataChanged(key, value);
                }

                @Override
                public void onAttrChanged(BaseTemplate key, String attr, Object value) {
                    if(listener != null)
                        listener.onAttrChanged(key, attr, value);
                }

                @Override
                public void onDatasChanged(Map<String, Object> map) {

                }
            });
        }
    }

}
