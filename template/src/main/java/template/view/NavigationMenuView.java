package template.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import template.com.form.R;

/**
 *  导航栏菜单
 */

public class NavigationMenuView extends ListView {

    public NavigationMenuView(Context context) {
        this(context, null);
    }

    public NavigationMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAdapter(new NavigationMenuAdapter(context, new String[]{"A","HHH"}, null));
    }

    public class NavigationMenuAdapter extends BaseAdapter {
        private Context context;
        private String[] data;
        private TemplateView templateView;

        public NavigationMenuAdapter(Context context, String[] data, TemplateView templateView){
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
                convertView = LayoutInflater.from(context).inflate(R.layout.template_common_menu, null);
            TextView textView = convertView.findViewById(R.id.common_menu_text);
            textView.setText(getItem(position).toString());
            return convertView;
        }
    }
}
