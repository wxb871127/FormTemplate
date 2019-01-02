package template.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import template.bean.SearchTemplate;
import template.com.form.R;

public class SearchTemplateDialog extends BaseTemplateDialog<SearchTemplate>{
    private View view;
    private EditText editText;
    private ListView listView;

    public SearchTemplateDialog(Context mContext) {
        super(mContext);
    }

    @Override
    public <S> void initDialog(SearchTemplate template, S value) {
        super.initDialog(template, value);
        view = LayoutInflater.from(mContext).inflate(R.layout.search_template_dialog, null);
        editText = (EditText) view.findViewById(R.id.template_search_dialog_text);
        listView = (ListView) view.findViewById(R.id.template_search_dialog_list);

        dialog = new AlertDialog.Builder(mContext).setTitle(template.label)
                .setView(view).create();
    }
}
