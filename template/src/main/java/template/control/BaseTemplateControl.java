package template.control;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.Map;

import base.util.CommonUtil;
import template.bean.BaseTemplate;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
import template.widget.OnTemplateListener;
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
    private OnTemplateListener listener;

    public abstract Class<? extends BaseTemplate> getTemplateClass();
    public abstract BaseTemplateView getTemplateView(Context context);

    public interface OnTemplateListener{
        public void onTemplateUpdate(String key, Object value);
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

    public BaseTemplateDialog getDialog(Context context){
        return null;
    }

    public boolean isEditable(Map<String, Object> valueMap){
       return CommonUtil.compute(template.editable, valueMap, true);
    }

    public boolean isShow(Map<String, Object> valueMap){
        return CommonUtil.compute(template.show, valueMap, true);
    }

    public void initView(final Context context, final BaseViewHolder holder, final T template, final Map<String, Object> valueMap){
        this.valueMap = valueMap;
        view = getTemplateView(context);
        view.initView(holder, template, template.getShowName(valueMap.get(template.name)),isEditable(valueMap));
        view.setShow(holder, isShow(valueMap));

        view.setOnTemplateListener(new template.widget.OnTemplateListener() {
            @Override
            public void onDataChange(Object object) {
//                valueMap.put(template.name, object);
                if(listener != null)
                    listener.onTemplateUpdate(template.name, object);
            }
        });

        view.setOnViewListener(new BaseTemplateView.OnViewListener() {
            @Override
            public void onViewClick() {
                dialog = getDialog(context);
                if(dialog != null) {
                    dialog.initDialog(template, valueMap.get(template.name));
                    dialog.showDialog();
                    dialog.setDialogListener(new BaseTemplateDialog.OnDialogListener() {

                        @Override
                        public void onDataChange(String s) {
//                            valueMap.put(template.name, s);
                            if(listener != null)
                                listener.onTemplateUpdate(template.name, s);
                        }

                        @Override
                        public void onDataClean() {
//                            valueMap.put(template.name, null);
                            if(listener != null)
                                listener.onTemplateUpdate(template.name, null);
                        }
                    });
                }
            }
        });
    }

}
