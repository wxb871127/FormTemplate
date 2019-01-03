package template.control;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.ListTemplate;
import template.widget.BaseTemplateView;
import template.widget.ListTemplateView;
import template.widget.OnTemplateListener;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.ListTemplateDialog;

@Template(tag = "list")
public class ListTemplateControl extends BaseTemplateControl{
    JSONArray jsonArray = null;

    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return ListTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(final Context context) {
        final ListTemplateView listTemplateView = new ListTemplateView(context);
        listTemplateView.setTemplateViewListener(new ListTemplateView.OnListTemplateViewListener() {
            @Override
            public void onDataDelete(int index) {
                jsonArray = (JSONArray) valueMap.get(template.name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    jsonArray.remove(index);
                }
                if(listener != null)
                    listener.onTemplateUpdate(template.name, jsonArray);
            }

            @Override
            public void onClickAdd() {
                dialog = getDialog(context);
                if (dialog != null) {
                    jsonArray = (JSONArray) valueMap.get(template.name);
                    dialog.initDialog(template, null);
                    dialog.setOnTemplateListener(new template.widget.OnTemplateListener() {
                        @Override
                        public void onDataChange(String name, Object object) {
                            jsonArray.put((JSONObject)object);
                            if(listener != null)
                                listener.onTemplateUpdate(name, jsonArray);
                        }
                    });
                    dialog.showDialog();
                }
            }

            @Override
            public void onItemViewClick(final int index) {
                dialog = getDialog(context);
                if(dialog != null) {
                    try {
                        jsonArray = (JSONArray) valueMap.get(template.name);
                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                        dialog.initDialog(template, jsonObject);
                        dialog.setOnTemplateListener(new template.widget.OnTemplateListener() {
                            @Override
                            public void onDataChange(String name, Object object) {
                                try {
                                    jsonArray.put(index, (JSONObject)object);
                                    if(listener != null)
                                        listener.onTemplateUpdate(name, jsonArray);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dialog.showDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return listTemplateView;
    }

    @Override
    public BaseTemplateDialog getDialog(Context context) {
        ListTemplateDialog dialog = new ListTemplateDialog(context);
        return dialog;
    }
}
