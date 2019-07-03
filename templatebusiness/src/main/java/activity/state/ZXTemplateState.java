package activity.state;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.business.annotation.State;

import org.json.JSONArray;
import org.json.JSONException;

import template.com.templatebusiness.R;
import template.view.TemplateView;


/**
 * 专项：编辑菜单；提交按钮
 */

@State(state = "ZX")
public class ZXTemplateState extends TemplateState {
    TemplateView templateView;

    public ZXTemplateState(Context context) {
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

//    @Override
//    public void initTemplateView(TemplateView templateView) {
//        templateView.initTemplate(getTemplateRes());
//        templateView.setValue("xm", "林浩");
//        templateView.setValue("jtdh","122");
//        templateView.setValue("jzlx","1");
//        templateView.setValue("lrr","124");
//        templateView.setValue("ycqshfszlcsxs","1,3");
//        try {
//            templateView.setValue("gmsList",new JSONArray("[ {\"xh\":\"1\",\"gmy\":\"1\",\"fsrq\":\"2018-11-21\",\"bz\":\"是非观\"}, {\"xh\":\"2\",\"gmy\":\"2\",\"fsrq\":\"2018-12-21\",\"bz\":\"手工课\"}]"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        templateView.setEditMode(false);
//        this.templateView = templateView;
//    }

    @Override
    public void initBottomView(ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        View view = LayoutInflater.from(context).inflate(R.layout.template_zx_state_bottom, viewGroup);
        view.findViewById(R.id.template_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("updateData is ", business.updateData);
            templateView.setEditMode(true);
            }
        });
        if(!Boolean.parseBoolean(business.edit)){
            view.setVisibility(View.GONE);
        }
    }
}
