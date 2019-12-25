package template.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import template.bean.SelectTemplate;
import template.com.form.R;
import template.view.MaxHeightListView;

public class SelectTemplateDialog extends BaseTemplateDialog<SelectTemplate> {

    private String[] items;
    private List<String> selectedItem;

    public SelectTemplateDialog(Context mContext) {
        super(mContext);
        selectedItem = new ArrayList<>();
    }

    @Override
    public void initDialog(final SelectTemplate template, Object value) {
        super.initDialog(template, value);
        java.lang.String v = "";
        if (value != null)
            v = value.toString();
        boolean[] checkedItem = template.getSelectItems(v);
//        items = template.getNames();
        items = template.getshowItemNames();
        selectedItem.clear();
//        if(v.contains(",")) {
        if (!TextUtils.isEmpty(v)) {
            java.lang.String[] vs = v.split(",");
            for (int i = 0; i < vs.length; i++)
                selectedItem.add(vs[i]);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_select_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.select_template_dialog_title);
        MaxHeightListView list = (MaxHeightListView) view.findViewById(R.id.radio_template_dialog_list);
        TextView clean = (TextView) view.findViewById(R.id.select_template_dialog_clean);
        TextView confirm = (TextView) view.findViewById(R.id.select_template_dialog_confirm);
        TextView cancel = (TextView) view.findViewById(R.id.select_template_dialog_cancel);

        list.setListViewHeight(mContext.getResources().getDimensionPixelOffset(R.dimen.dp300));
        list.setAdapter(new SelectDialogAdapter(items));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem.clear();
                if (listener != null)
                    listener.onDataChanged(template, "", true);
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = "";
                for (int i = 0; i < selectedItem.size(); i++) {
                    if (i != selectedItem.size() - 1)
                        selected += selectedItem.get(i) + ",";
                    else selected += selectedItem.get(i);
                }
                if (listener != null)
                    listener.onDataChanged(template, selected, true);
                dialog.dismiss();
            }
        });

        dialog = new Dialog(mContext);
        dialog.setContentView(view);

        if (!TextUtils.isEmpty(template.label)) {
            title.setText(template.label);
        } else {
            title.setText("");
        }
    }

    public class SelectDialogAdapter extends android.widget.BaseAdapter {

        private String[] items;

        public SelectDialogAdapter(String[] items) {
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
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.template_select_dialog_item, null);
                holder = new ViewHolder();
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.template_select_dialog_item_checkbox);
                holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkBox.setText(items[position]);
            //复用时，先清空监听
            holder.checkBox.setOnCheckedChangeListener(null);

            if (selectedItem.contains(template.getCode(items[position]))){
                holder.checkBox.setChecked(true);
            }else{
                holder.checkBox.setChecked(false);
            }


            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItem.contains(template.getCode(items[position]))){
                        holder.checkBox.setChecked(false);
                    }else{
                        holder.checkBox.setChecked(true);
                    }
                }
            });
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        selectedItem.add(template.getCode(items[position]));
                    else
                        selectedItem.remove(template.getCode(items[position]));
                }
            });

            return convertView;
        }

        class ViewHolder {
            private CheckBox checkBox;
            private LinearLayout ll;
        }
    }

}
