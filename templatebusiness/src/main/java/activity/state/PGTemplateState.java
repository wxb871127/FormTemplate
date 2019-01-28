package activity.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;

import com.business.annotation.State;

import template.com.form.R;
import template.view.TemplateView;

/**
 * 评估表单
 */
@State(state = "PG")
public class PGTemplateState extends TemplateState{

    public PGTemplateState(Context context) {
        super(context);
    }

    @Override
    public void addMenuView(Menu menu) {

    }

    @Override
    public void onMenuSelected(int id) {

    }

    @Override
    public void initBottomView(ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        LayoutInflater.from(context).inflate(R.layout.template_zx_state_bottom, viewGroup);
    }
}
