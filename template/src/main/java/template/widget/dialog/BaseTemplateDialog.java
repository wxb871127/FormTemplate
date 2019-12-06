package template.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import template.bean.BaseTemplate;
import template.interfaces.OnTemplateListener;

public class BaseTemplateDialog<T extends BaseTemplate> {
    protected T template;
    Object value;
    protected Context mContext;
    protected Dialog dialog;
    protected OnTemplateListener listener;

    public void setOnTemplateListener(OnTemplateListener listener){
        this.listener = listener;
    }

    public BaseTemplateDialog(Context mContext){
        this.mContext = mContext;
    }

    public void initDialog(T template, Object value){
        this.template = template;
        this.value = value;
    }

    public void updateDialog(Object value){
        this.value = value;
    }

    public void showDialog(){
        if(dialog != null) {

            dialog.show();
        }
    }

}
