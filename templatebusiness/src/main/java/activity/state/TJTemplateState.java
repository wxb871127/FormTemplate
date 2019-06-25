package activity.state;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.business.annotation.State;

import org.json.JSONObject;

import java.util.Map;

import template.com.templatebusiness.R;
import template.view.TemplateView;

/**
 *
 */
@State(state = "TJ")
public class TJTemplateState extends TemplateState{

    public TJTemplateState(Context context) {
        super(context);
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
    protected void setTemplateFlag() {
        super.setTemplateFlag();
        templateView.addFlags(TemplateView.FLAG_EXCEPTION | TemplateView.FLAG_REFUSE);
    }

    @Override
    public void initBottomView(ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        View view = LayoutInflater.from(context).inflate(R.layout.template_zx_state_bottom, viewGroup);
        view.findViewById(R.id.template_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = getValueData();
                Log.e("xxxxxxx", jsonObject.toString());
            }
        });
    }
}
