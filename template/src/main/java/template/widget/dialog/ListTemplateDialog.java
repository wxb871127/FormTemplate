package template.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.Map;
import template.bean.ListTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.view.TemplateView;

public class ListTemplateDialog extends BaseTemplateDialog<ListTemplate>{
    private TemplateView templateView;
    public ListTemplateDialog(Context mContext) {
        super(mContext);

    }

    @Override
    public void initDialog(final ListTemplate template, Object value) {
        super.initDialog(template, value);
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_template_dialog, null);
        templateView = view.findViewById(R.id.templateView);
        templateView.initTemplate(template.templates);
         if(value != null) {
            JSONObject jsonObject = (JSONObject) value;
            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                try {
                    String key = it.next();
                    String tvalue = jsonObject.getString(key);
                    if(tvalue.equalsIgnoreCase("null"))
                        templateView.setValue(key, null);
                    else {
                        TemplateValue templateValue = new TemplateValue();
                        templateValue.value = tvalue;
                        templateView.setValue(key, templateValue);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        dialog = new Dialog(mContext);
        dialog.setContentView(view);
        View cancel = view.findViewById(R.id.btn_cancel);
        View sure = view.findViewById(R.id.btn_sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog != null)
                    dialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!templateView.checkRequired())
                    return;
                Map<String, TemplateValue> map = templateView.getValueMap();
                JSONObject jsonObject = new JSONObject();
                for(String key : map.keySet()){
                    try {
                        jsonObject.put(key, map.get(key).value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(dialog != null)
                    dialog.dismiss();
                if(listener != null)
                    listener.onDataChanged(template, jsonObject, true);
            }
        });
        dialog.setTitle(template.label);
    }
}
