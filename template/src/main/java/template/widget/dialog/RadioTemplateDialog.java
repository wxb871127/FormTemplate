package template.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import template.bean.RadioTemplate;
import template.com.form.R;
import template.view.MaxHeightListView;

public class RadioTemplateDialog extends BaseTemplateDialog<RadioTemplate> {
    private View view;
    RadioDialogAdapter radioAdapter;

    public RadioTemplateDialog(Context mContext) {
        super(mContext);
    }

    @Override
    public void initDialog(final RadioTemplate template, Object value) {
        super.initDialog(template, value);
        view = LayoutInflater.from(mContext).inflate(R.layout.template_radio_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.radio_template_dialog_title);
        MaxHeightListView list = (MaxHeightListView) view.findViewById(R.id.radio_template_dialog_list);
        TextView clean = (TextView) view.findViewById(R.id.radio_template_dialog_clean);

        dialog = new Dialog(mContext);
        dialog.setContentView(view);


        radioAdapter = new RadioDialogAdapter(template.getshowItemNames());
        list.setListViewHeight(mContext.getResources().getDimensionPixelOffset(R.dimen.dp300));
        list.setAdapter(radioAdapter);
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onDataChanged(template, "", true);
                dialog.dismiss();
            }
        });
        if (!TextUtils.isEmpty(template.label)) {
            title.setText(template.label);
        }

    }

    @Override
    public void updateDialog(Object value) {
        super.updateDialog(value);
        radioAdapter.notifyDataSetChanged();
    }


    public class RadioDialogAdapter extends android.widget.BaseAdapter {

        private String[] items;

        public RadioDialogAdapter(String[] items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            if (items == null) return 0;
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.template_radio_dialog_item, null);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);

            itemName.setText(items[position]);
            String v = "";
            if (value != null)
                v = value.toString();
            String name = template.getNameByCode(v);
            if (!TextUtils.isEmpty(name) && name.equals(getItem(position).toString())) {
                icon.setSelected(true);
            }
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = template.getCode(items[position]);
                    if (listener != null)
                        listener.onDataChanged(template, code, true);
                    dialog.dismiss();
                }
            });

            return convertView;
        }
    }
}
