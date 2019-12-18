package activity.state;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.business.annotation.State;
import com.convert.TjConverterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import template.com.templatebusiness.R;
import template.view.TemplateView;

/**
 *
 */
@State(state = "TJ")
public class TJTemplateState extends TemplateState{
    private boolean edit = true;

    public TJTemplateState(Context context) {
        super(context);
        factory = new TjConverterFactory();
    }

    @Override
    public void addMenuView(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "编辑").setIcon(R.drawable.template_ico_edit)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE,2,Menu.NONE,"设备").setIcon(R.drawable.template_ico_import)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onMenuSelected(int id) {
        switch (id){
            case 1:
                edit = !edit;
                templateView.setEditMode(edit);
                break;
            case 2:
                templateView.setDataSource("A001", "85");
                break;
        }
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
                if(!templateView.checkRequired())
                    return;
                JSONObject jsonObject = getValueData();
                Log.e("xx", jsonObject.toString());
                try {
                    Map map = new HashMap();
                    map.put("show", "李朝辉");
                    JSONObject json = new JSONObject();
                    json.put("name", "林浩");
                    json.put("num", 100);
                    map.put("value", json);
                    map.put("exception", true);

                    templateView.setCommandValue("1001", map);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        if(!Boolean.parseBoolean(business.edit)){
            view.setVisibility(View.GONE);
        }
    }
}
