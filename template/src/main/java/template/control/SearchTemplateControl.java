package template.control;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.SearchTemplate;
import template.bean.TemplateValue;
import template.config.TemplateConfig;
import template.widget.BaseTemplateView;
import template.widget.SearchTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.SearchTemplateDialog;

@Template(tag="search")
public class SearchTemplateControl<T extends BaseTemplate> extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return SearchTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        this.context = context;
        return new SearchTemplateView(context);
    }

    @Override
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        SearchTemplateDialog dialog = new SearchTemplateDialog(context);
        return dialog;
    }

    @Override
    protected void onClickHolder(BaseTemplate template, TemplateValue templateValue) {
        if(!templateValue.editable) return;
        super.onClickHolder(template, templateValue);
    }

    @Override
    protected void onDialogDataChanged(BaseTemplate template1, Object object, boolean notify) {
        SearchTemplate template = (SearchTemplate)template1;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(template.uri), null,
                template.primaryKey+" = ?", new String[]{object.toString()}, null, null);
        cursor.moveToFirst();
        Map map = new HashMap<>();
        for(String column : template.columnMap.keySet()) {
            int index = cursor.getColumnIndex(column);
            String columnValue = cursor.getString(index);
            map.put(template.columnMap.get(column), columnValue);
        }
        map.put(template1.name, object);
        if(listener != null)
            listener.onDatasChanged(map, notify);
    }
}
