package activity.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import org.w3c.dom.Element;

import template.com.form.R;
import template.widget.TemplateView;

/**
 * 评估表单
 */
public class PGTemplateState extends TemplateState{

    @Override
    public String getBusinessType() {
        return "PG";
    }

    @Override
    public void addMenuView(Menu menu) {

    }

    @Override
    public void onMenuSelected(int id) {

    }

    @Override
    public void initTempltaView(TemplateView templateView) {
        templateView.initTemplate(getTemplateRes());
    }

    @Override
    public void showBottomView(Context context, ViewGroup parent) {
        parent.removeAllViews();
        LayoutInflater.from(context).inflate(R.layout.zx_template_state, parent, true);
    }
}
