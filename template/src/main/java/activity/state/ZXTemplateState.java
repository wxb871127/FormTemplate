package activity.state;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Element;

import template.com.form.R;
import template.widget.TemplateView;
import template.widget.decorator.AbstractTemplateDecorator;
import template.widget.decorator.TemplateViewDecor;

/**
 * 专项表单
 */

public class ZXTemplateState extends TemplateState {
    TemplateView templateView;
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
    public void initTempltaView(AbstractTemplateDecorator templateView) {
        templateView.initTemplate(getTemplateRes());
        templateView.setValue("xm", "林浩");
        templateView.setValue("jtdh","122");
        templateView.setValue("jzlx","1");
        templateView.setValue("lrr","124");
        templateView.setValue("ycqshfszlcsxs","1,3");
        try {
            templateView.setValue("gmsList",new JSONArray("[ {\"xh\":\"1\",\"gmy\":\"1\",\"fsrq\":\"2018-11-21\",\"bz\":\"是非观\"}, {\"xh\":\"2\",\"gmy\":\"2\",\"fsrq\":\"2018-12-21\",\"bz\":\"手工课\"}]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        templateView.setEditMode(false);
        this.templateView = templateView;
    }

    @Override
    public void showBottomView(Context context, ViewGroup parent) {
        parent.removeAllViews();
        View view = LayoutInflater.from(context).inflate(R.layout.zx_template_state, parent, true);
        view.findViewById(R.id.template_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("updateData is ", business.updateData);
            templateView.setEditMode(true);
            }
        });
    }
}
