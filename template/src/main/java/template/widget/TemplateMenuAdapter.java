package template.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import template.com.form.R;

public class TemplateMenuAdapter extends BaseAdapter {
    private Context context;
    private String[] data;
    private TemplateView templateView;

    public TemplateMenuAdapter(Context context, String[] data, TemplateView templateView){
        this.context = context;
        this.data = data;
        this.templateView = templateView;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.common_menu, null);
        TextView textView = convertView.findViewById(R.id.common_menu_text);
        textView.setText(getItem(position).toString());
        return convertView;
    }
}
