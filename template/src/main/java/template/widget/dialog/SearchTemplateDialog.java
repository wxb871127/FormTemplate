package template.widget.dialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import template.bean.SearchTemplate;
import template.com.form.R;
import template.view.MaxHeightListView;

public class SearchTemplateDialog extends BaseTemplateDialog<SearchTemplate> {
    private View view;
    private EditText editText;
    private ImageView searchHint;
    private MaxHeightListView listView;
    private Adapter adapter;
    private TextView title;

    public SearchTemplateDialog(Context mContext) {
        super(mContext);
    }

    @Override
    public void initDialog(final SearchTemplate template, Object value) {
        super.initDialog(template, value);
        view = LayoutInflater.from(mContext).inflate(R.layout.template_search_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.search_template_dialog_title);
        searchHint = view.findViewById(R.id.search_template_dialog_hint);
        editText = (EditText) view.findViewById(R.id.template_search_dialog_text);
        listView = (MaxHeightListView) view.findViewById(R.id.template_search_dialog_list);
        adapter = new Adapter(mContext, null);
        listView.setListViewHeight(mContext.getResources().getDimensionPixelOffset(R.dimen.dp300));
        listView.setAdapter(adapter);
        title.setText(template.label);
        if (editText.getTag() instanceof TextWatcher) {//防止recyclerView刷新 触发TextWatcher事件
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
        if (value != null) {
            editText.setText(template.getShowName(value.toString(), mContext));
            searchHint.setVisibility(View.GONE);
        } else
            searchHint.setVisibility(View.VISIBLE);
//            editText.setText(value.toString());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    searchHint.setVisibility(View.GONE);
                    String selection = template.selection;
                    int count = 0;
                    while (selection.indexOf("?") != -1) {
                        selection = selection.substring(selection.indexOf("?") + 1);
                        count++;
                    }
                    String[] selectionArg = new String[count];
                    for (int i = 0; i < count; i++)
                        selectionArg[i] = s.toString();

                    ContentResolver contentResolver = mContext.getContentResolver();
                    Cursor cursor = contentResolver.query(Uri.parse(template.uri), null,
                            template.selection, selectionArg, null, null);
                    cursor.moveToFirst();
                    adapter.changeCursor(cursor);
                } else
                    searchHint.setVisibility(View.VISIBLE);
            }
        };
        editText.setTag(textWatcher);
        editText.addTextChangedListener(textWatcher);

        dialog = new Dialog(mContext);
        dialog.setContentView(view);
//        dialog = new AlertDialog.Builder(mContext).setTitle(template.label)
//                .setView(view).create();
    }

    public class Adapter extends CursorAdapter {

        public Adapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.template_search_dialog_item, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvText = (TextView) view.findViewById(R.id.template_search_dialog_item_text);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.template_search_dialog_item_checkbox);
            final String showColumn = cursor.getString(cursor.getColumnIndex(template.showColumn));
            final String primaryKey = cursor.getString(cursor.getColumnIndex(template.primaryKey));
            tvText.setText(showColumn);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onDataChanged(template, primaryKey, true);
                    if (dialog != null)
                        dialog.dismiss();
                }
            });
        }
    }
}
