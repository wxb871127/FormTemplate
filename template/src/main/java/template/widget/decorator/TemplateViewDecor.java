package template.widget.decorator;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import template.bean.SectionTemplate;
import template.bean.TemplateList;
import template.com.form.R;
import template.widget.TemplateMenuAdapter;
import template.widget.TemplateMenuView;

public class TemplateViewDecor extends AbstractTemplateDecorator{
    private ActionBarDrawerToggle drawerToggle;

    public TemplateViewDecor(Context context) {
        super(context);
    }

    public TemplateViewDecor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TemplateViewDecor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayout() {
        return R.layout.template_drawer;
    }

    @Override
    public void setNavigationBar(View view, Activity activity) {
        if(view != null){
            LinearLayout frameLayout = (LinearLayout) mView.findViewById(R.id.drawer_menu);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    (int)mContext.getResources().getDimension(R.dimen.dp100),
                    ViewGroup.LayoutParams.MATCH_PARENT);
//            if(getTemplateList() == null)
//                return;
//            TemplateList sectionTemplateList = getTemplateList().subList(SectionTemplate.class);
//            if(sectionTemplateList == null || sectionTemplateList.size()==0)
//                return;
//
//            String[] data = new String[sectionTemplateList.size()];
//            for(int i=0; i<sectionTemplateList.size(); i++)
//                data[i] = sectionTemplateList.get(i).label;

            String[] data = new String[]{"A","B","Test"};

            ((TemplateMenuView)view).setAdapter(new TemplateMenuAdapter(mContext, data,null ));
            frameLayout.addView(view, layoutParams);
            drawerToggle = new ActionBarDrawerToggle(activity,
                    (DrawerLayout) mView,
                    null,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            ((DrawerLayout)mView).addDrawerListener(drawerToggle);
        }
    }

    @Override
    public void setTheme() {

    }
}
