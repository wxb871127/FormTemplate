package template.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;

import template.bean.BaseTemplate;

public class BaseTemplateDialog<T extends BaseTemplate> {
    protected T template;
    Object value;
    protected Context mContext;
    protected AlertDialog dialog;
    protected OnDialogListener listener;

    //弹出框接口支持数据 修改、清空
    public interface OnDialogListener{
        public void onDataChange(String s);
        public void onDataClean();
    }

    public void setDialogListener(OnDialogListener listener){
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
