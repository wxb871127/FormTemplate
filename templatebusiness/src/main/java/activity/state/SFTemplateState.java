package activity.state;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.business.annotation.State;

import org.json.JSONObject;

import template.com.templatebusiness.R;


/**
 * 随访：编辑、引入、设备菜单；提交、暂存按钮
 */

@State(state = "SF")
public class SFTemplateState extends TemplateState{
    public SFTemplateState(Context context) {
        super(context);
    }

    @Override
    public void addMenuView(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "编辑").setIcon(R.drawable.template_ico_edit)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 2, Menu.NONE, "引入").setIcon(R.drawable.template_ico_import)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 3, Menu.NONE, "设备").setIcon(R.drawable.template_ico_device)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onMenuSelected(int id) {

    }

    @Override
    public void initBottomView(ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        View view = LayoutInflater.from(context).inflate(R.layout.template_sf_state_bottom, viewGroup);
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
                JSONObject jsonObject = getValueData();
                Log.e("xxxxxxx", jsonObject.toString());
            }
        });
        if(!Boolean.parseBoolean(business.edit)){
            view.setVisibility(View.GONE);
        }
    }
}
