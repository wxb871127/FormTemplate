package template.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import template.bean.ListTemplate;
import template.widget.TemplateView;

public class ListTemplateDialog extends BaseTemplateDialog<ListTemplate>{

    public interface OnDialogListener{
        public void onDataAdd(Object s);
    }

    public ListTemplateDialog(Context mContext) {
        super(mContext);
    }

    @Override
    public <S> void initDialog(ListTemplate template, S value) {
        super.initDialog(template, value);
        TemplateView templateView = new TemplateView(mContext);
        templateView.initTemplate(template.templates);
        if(value != null) {
            JSONObject jsonObject = (JSONObject) value;
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                try {
                    String key = it.next();
                    String jvalue = jsonObject.getString(key);
                    templateView.setValue(key, jvalue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        dialog = new AlertDialog.Builder(mContext).setView(templateView)
                .setPositiveButton("确定", null).create();



    }
}
