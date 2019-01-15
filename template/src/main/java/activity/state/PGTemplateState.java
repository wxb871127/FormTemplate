package activity.state;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;

import template.com.form.R;
import template.widget.TemplateView;

/**
 * 评估表单
 */
public class PGTemplateState extends TemplateState{

    public PGTemplateState(Context context, Activity activity) {
        super(context, activity);
    }

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
    public void initTemplateView(TemplateView templateView) {
        templateView.initTemplate(getTemplateRes());
    }

    @Override
    public void initBottomView() {
        LayoutInflater.from(context).inflate(R.layout.zx_template_state, null);
    }
}
