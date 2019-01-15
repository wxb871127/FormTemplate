package template.widget.decorator;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import template.com.form.R;

/**
 *   drawerLayout的导航装饰
 */
public class TemplateViewDrawerDecor extends AbstractTemplateDecorator{
    private ActionBarDrawerToggle drawerToggle;

    public TemplateViewDrawerDecor(Context context) {
        super(context);
    }

    public TemplateViewDrawerDecor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TemplateViewDrawerDecor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayout() {
        return R.layout.template_drawer;
    }

    @Override
    public void setTheme(int theme) {

    }

    @Override
    public void setDecoratorView(ViewGroup viewGroup, Activity activity) {
//        initView(null);
        mView =  LayoutInflater.from(mContext).inflate(getLayout(), viewGroup);
        DrawerLayout drawerLayout = mView.findViewById(R.id.drawer_layout);
        TemplateMenuView templateMenuView = mView.findViewById(R.id.drawer_menu);

//        if(getTemplateList() == null)
//            return;
//        TemplateList sectionTemplateList = getTemplateList().subList(SectionTemplate.class);
//        if(sectionTemplateList == null || sectionTemplateList.size()==0)
//            return;
//        String[] data = new String[sectionTemplateList.size()];
//        for(int i=0; i<sectionTemplateList.size(); i++)
//            data[i] = sectionTemplateList.get(i).label;

        String[] data = new String[]{"A","B","Test"};
        templateMenuView.setAdapter(new TemplateMenuAdapter(mContext, data,null ));

        drawerToggle = new ActionBarDrawerToggle(activity,
                drawerLayout,
                null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
    }


}
