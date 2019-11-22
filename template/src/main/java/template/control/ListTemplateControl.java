package template.control;

import android.content.Context;
import android.os.Build;

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
public class ListTemplateControl<T extends BaseTemplate> extends BaseTemplateControl{
    JSONArray jsonArray = null;

    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return ListTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(final Context context) {
        this.context = context;
        final ListTemplateView listTemplateView = new ListTemplateView(context);
        listTemplateView.setTemplateViewListener(new ListTemplateView.OnListTemplateViewListener() {
            @Override
            public void onDataDelete(int index) {
                jsonArray = (JSONArray) valueMap.get(template.name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    jsonArray.remove(index);
                }
                if(jsonArray.length() == 0)
                    jsonArray = null;
                if(listener != null)
                    listener.onDataChanged(template, jsonArray);
            }

            @Override
            public void onClickAdd() {
                dialog = getDialog(context, null);
                if (dialog != null) {
                    jsonArray = (JSONArray) valueMap.get(template.name);
                    dialog.initDialog(template, null);
                    dialog.setOnTemplateListener(new template.widget.OnTemplateListener() {
                        @Override
                        public void onDataChange(BaseTemplate name, Object object) {
                            if(jsonArray == null)
                                jsonArray = new JSONArray();
                            jsonArray.put((JSONObject) object);
                            if(listener != null)
                                listener.onDataChanged(name, jsonArray);
                        }

                        @Override
                        public void onAttrClick(BaseTemplate template, String attrName, Object value) {

                        }
                    });
                    dialog.showDialog();
                }
            }

            @Override
            public void onItemViewClick(final int index) {
                dialog = getDialog(context, null);
                if(dialog != null) {
                    try {
                        jsonArray = (JSONArray) valueMap.get(template.name);
                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                        dialog.initDialog(template, jsonObject);
                        dialog.setOnTemplateListener(new template.widget.OnTemplateListener() {
                            @Override
                            public void onDataChange(BaseTemplate name, Object object) {
                                try {
                                    jsonArray.put(index, (JSONObject)object);
                                    if(listener != null)
                                        listener.onDataChanged(name, jsonArray);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAttrClick(BaseTemplate template, String attrName, Object value) {

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
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        ListTemplateDialog dialog = new ListTemplateDialog(context);
        return dialog;
    }
}
