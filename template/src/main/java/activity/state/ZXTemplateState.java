package activity.state;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.w3c.dom.Element;

import template.com.form.R;

/**
 * 专项表单
 */

public class ZXTemplateState extends TemplateState {

    @Override
    public String getBusinessType() {
        return "ZX";
    }

    @Override
    public void addMenuView(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "编辑").setIcon(R.drawable.ico_edit)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onMenuSelected(int id) {

    }

    @Override
    public void showBottomView(Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.zx_template_state, parent, true);
        view.findViewById(R.id.template_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("updateData is ", business.updateData);

            }
        });
    }
}
