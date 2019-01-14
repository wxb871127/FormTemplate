package activity.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import template.com.form.R;
import template.widget.TemplateView;
import template.widget.decorator.AbstractTemplateDecorator;
import template.widget.decorator.TemplateViewDecor;

/**
 * 随访表单
 */

public class SFTemplateState extends TemplateState{
    @Override
    public String getBusinessType() {
        return "SF";
    }

    @Override
    public void addMenuView(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "编辑").setIcon(R.drawable.ico_edit)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 2, Menu.NONE, "引入").setIcon(R.drawable.ico_import)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 3, Menu.NONE, "设备").setIcon(R.drawable.ico_device)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onMenuSelected(int id) {

    }

    @Override
    public void initTempltaView(AbstractTemplateDecorator templateView) {
        templateView.initTemplate(getTemplateRes());
        templateView.setValue("sg","174");
        templateView.setValue("tz","68");
        templateView.notifyData();
    }

    @Override
    public void showBottomView(Context context, ViewGroup parent) {
        parent.removeAllViews();
        View view = LayoutInflater.from(context).inflate(R.layout.sf_template_state, parent, true);
        Button save = view.findViewById(R.id.template_save);
        Button submit = view.findViewById(R.id.template_submit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
