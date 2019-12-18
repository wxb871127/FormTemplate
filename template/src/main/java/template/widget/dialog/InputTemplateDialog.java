package template.widget.dialog;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.LinkedHashMap;
import java.util.Map;
import template.bean.InputTemplate;
import template.com.form.R;

/**
 *   常用语弹出框
 */
public class InputTemplateDialog extends BaseTemplateDialog<InputTemplate>{
    private View view;
    private ListView listView;
    private Button btn_cancel;
    private Button btn_submit;
    private Adapter adapter;
    private Map<String, String> map = new LinkedHashMap<>();;
    public InputTemplateDialog(Context mContext) {
        super(mContext);
    }

    OnInputDialogListener listener;
    public interface OnInputDialogListener{
        void addQuote(String quote);
    }

    public void setListener(OnInputDialogListener listener){
        this.listener = listener;
    }

    @Override
    public void initDialog(final InputTemplate template, Object value) {
        super.initDialog(template, value);
        view = LayoutInflater.from(mContext).inflate(R.layout.template_input_dialog, null);
        listView =  view.findViewById(R.id.template_search_dialog_list);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_submit = view.findViewById(R.id.btn_submit);
        adapter = new Adapter(mContext, null);
        listView.setAdapter(adapter);

        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(template.uri), null,
                template.primaryKey+" = ?" , new String[]{template.name}, null, null);
        cursor.moveToFirst();
        adapter.changeCursor(cursor);

        dialog = new AlertDialog.Builder(mContext).setTitle(template.label+"-常用语")
                .setView(view).create();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog != null)
                    dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    StringBuilder builder = new StringBuilder();
                    for(String key: map.keySet()){
                        builder.append(key);
                    }
                    map.clear();
                    listener.addQuote(builder.toString());
                }
                if(dialog != null)
                    dialog.dismiss();
            }
        });
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
            tvText.setText(showColumn);
            checkBox.setVisibility(View.VISIBLE);


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        map.put(showColumn, showColumn);
                    }else {
                        map.remove(showColumn);
                    }
                }
            });

        }
    }
}
