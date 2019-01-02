package template.control;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.ListTemplate;
import template.widget.BaseTemplateView;
import template.widget.ListTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.ListTemplateDialog;

@Template(tag = "list")
public class ListTemplateControl extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return ListTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(final Context context) {
        ListTemplateView listTemplateView = new ListTemplateView(context);
        listTemplateView.setTemplateViewListener(new ListTemplateView.OnListTemplateViewListener() {
            @Override
            public void onDataDelete(Object object) {

            }

            @Override
            public void onClickAdd() {
                dialog = getDialog(context);
                if (dialog != null) {
                    dialog.initDialog(template, null);
                    dialog.showDialog();
                }
            }

            @Override
            public void onItemViewClick(int index) {
                dialog = getDialog(context);
                if(dialog != null) {
                    try {
                        JSONArray jsonArray = (JSONArray) valueMap.get(template.name);
                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                        dialog.initDialog(template, jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialog.showDialog();
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
