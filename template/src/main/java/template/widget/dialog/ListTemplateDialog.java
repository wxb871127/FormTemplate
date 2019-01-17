package template.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import template.bean.ListTemplate;
import template.view.TemplateView;

public class ListTemplateDialog extends BaseTemplateDialog<ListTemplate>{

    public ListTemplateDialog(Context mContext) {
        super(mContext);
    }

    @Override
    public <S> void initDialog(final ListTemplate template, S value) {
        super.initDialog(template, value);
        final TemplateView templateView = new TemplateView(mContext);
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
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> map = templateView.getValueMap();
                        JSONObject jsonObject = new JSONObject(map);
                        if(listener != null)
                            listener.onDataChange(template.name, jsonObject);
                    }
                }).setNegativeButton("取消", (DialogInterface.OnClickListener) null)
                .create();
        dialog.setTitle(template.label);
    }
}
