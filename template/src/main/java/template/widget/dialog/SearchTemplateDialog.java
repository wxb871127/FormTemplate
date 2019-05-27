package template.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Camera;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.templatedb.greendao.AreaDao;

import org.greenrobot.greendao.database.Database;

import template.bean.SearchTemplate;
import template.com.form.R;
import template.com.templatedb.Area;
import template.com.templatedb.DaoManager;
import template.com.templatedb.Drug;

public class SearchTemplateDialog extends BaseTemplateDialog<SearchTemplate>{
    private View view;
    private EditText editText;
    private ListView listView;
    private Adapter adapter;

    public SearchTemplateDialog(Context mContext) {
        super(mContext);
    }

    @Override
    public <S> void initDialog(final SearchTemplate template, S value) {
        super.initDialog(template, value);
        view = LayoutInflater.from(mContext).inflate(R.layout.search_template_dialog, null);
        editText = (EditText) view.findViewById(R.id.template_search_dialog_text);
        listView = (ListView) view.findViewById(R.id.template_search_dialog_list);
        adapter = new Adapter(mContext, null);
        listView.setAdapter(adapter);

        if(editText.getTag() instanceof TextWatcher){//防止recyclerView刷新 触发TextWatcher事件
            editText.removeTextChangedListener((TextWatcher)editText.getTag());
        }
        if(value != null)
            editText.setText(template.getShowName(value.toString(), mContext));
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
                    String where = "select * from " + template.style + " where " + template.selection;
                    Database database = DaoManager.getInstance(mContext).getDaoSession().getDatabase();
                    Cursor cursor = database.rawQuery(where, new String[]{s.toString(), s.toString()});
                    cursor.moveToFirst();
                    adapter.changeCursor(cursor);
                }
            }
        };
        editText.setTag(textWatcher);
        editText.addTextChangedListener(textWatcher);
        dialog = new AlertDialog.Builder(mContext).setTitle(template.label)
                .setView(view).create();
    }

    public class Adapter extends CursorAdapter{

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
                    if(listener != null)
                        listener.onDataChange(template, primaryKey);
                    if(dialog != null)
                        dialog.dismiss();
                }
            });
        }
    }
}
