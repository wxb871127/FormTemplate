package template.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;

import template.bean.BaseTemplate;
import template.widget.OnTemplateListener;

public class BaseTemplateDialog<T extends BaseTemplate> {
    protected T template;
    Object value;
    protected Context mContext;
    protected AlertDialog dialog;
    protected OnTemplateListener listener;

    public void setOnTemplateListener(OnTemplateListener listener){
        this.listener = listener;
    }

    public BaseTemplateDialog(Context mContext){
        this.mContext = mContext;
    }

    public <S extends Object> void initDialog(T template, S value){
        this.template = template;
        this.value = value;
    }

    public void updateDialog(Object value){
        this.value = value;
    }

    public void showDialog(){
        if(dialog != null)
            dialog.show();
    }

}
