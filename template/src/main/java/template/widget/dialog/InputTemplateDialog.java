package template.widget.dialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedHashMap;
import java.util.Map;

import template.bean.InputTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.view.MaxHeightListView;

/**
 * 常用语弹出框
 */
public class InputTemplateDialog extends BaseTemplateDialog<InputTemplate> {
    private View view;
    private TextView title;
    private MaxHeightListView listView;
    private Button btn_cancel;
    private Button btn_submit;
    private Adapter adapter;
    private Map<String, String> map = new LinkedHashMap<>();
    ;

    public InputTemplateDialog(Context mContext) {
        super(mContext);
    }

    OnInputDialogListener listener;

    public interface OnInputDialogListener {
        void addQuote(String quote);
    }

    public void setListener(OnInputDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void initDialog(final InputTemplate template, Object value) {
        super.initDialog(template, value);
        view = LayoutInflater.from(mContext).inflate(R.layout.template_input_dialog, null);
        title = view.findViewById(R.id.input_template_dialog_title);
        listView = view.findViewById(R.id.template_search_dialog_list);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_submit = view.findViewById(R.id.btn_submit);
        adapter = new Adapter(mContext, null);
        listView.setListViewHeight(mContext.getResources().getDimensionPixelOffset(R.dimen.dp266));
        listView.setAdapter(adapter);

        title.setText(template.label + "-常用语");
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(template.uri), null,
                template.primaryKey + " = ?", new String[]{template.name}, null, null);
        cursor.moveToFirst();
        adapter.changeCursor(cursor);


        dialog = new Dialog(mContext);
        dialog.setContentView(view);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null)
                    dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    StringBuilder builder = new StringBuilder();
                    for (String key : map.keySet()) {
                        builder.append(key);
                    }
                    map.clear();
                    listener.addQuote(builder.toString());
                }
                if (dialog != null)
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
            return LayoutInflater.from(context).inflate(R.layout.template_input_dialog_item, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll);
            TextView tvText = (TextView) view.findViewById(R.id.template_search_dialog_item_text);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.template_search_dialog_item_checkbox);
            final String showColumn = cursor.getString(cursor.getColumnIndex(template.showColumn));
            tvText.setText(showColumn);
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(map.containsKey(showColumn));

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.performClick();
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        map.put(showColumn, showColumn);
                    else
                        map.remove(showColumn);
                }
            });
        }
    }
}
