package activity.state;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;

import com.business.annotation.State;
import org.w3c.dom.Element;

import template.com.templatebusiness.R;
import template.view.NavigationMenuView;
import template.view.NavigationTemplateView;
import template.view.SlidPageTemplateView;
import template.view.TemplateView;


public abstract class TemplateState {
    enum TemplateBusiness {
        ZX,
        PG,
        SF;
        protected String getData;
        protected String updateData;
        protected String name;
        protected String res;//对应的表单xml文件
        protected String style;//0 不带装饰 1 带有导航栏 2左右滑动页 3带导航的左右滑动页
    }
    TemplateBusiness business;
    protected Context context;

    public TemplateState(Context context){
        this.context = context;
    }

    public void parseBusiness(Element element){
        State state= this.getClass().getAnnotation(State.class);
        business = TemplateBusiness.valueOf(state.state());
        business.getData = element.getAttribute("getData");
        business.name = element.getAttribute("name");
        business.res = element.getAttribute("res");
        business.updateData = element.getAttribute("updateData");
        business.style = element.getAttribute("style");
    }

    public String getTemplateName(){
        return business.name;
    }

    public String getTemplateRes(){
        return business.res;
    }

    public void initContentView(ViewGroup viewGroup){
        TemplateView templateView = null;
        if("0".equals(business.style)){
            templateView = new TemplateView(context);
            viewGroup.addView(templateView);
            templateView.initTemplate(business.res);
//            initTemplateView(templateView);
        }else if("1".equals(business.style)){
            NavigationTemplateView navigationTemplateView = new NavigationTemplateView(context);
            viewGroup.addView(navigationTemplateView);
        }else if("2".equals(business.style)){
//            SlidPageTemplateView slidPageTemplateView = new SlidPageTemplateView(context);
//            viewGroup.addView(slidPageTemplateView);
            templateView = new TemplateView(context);
            viewGroup.addView(templateView);
            templateView.initTemplate(business.res);
        }

    }

    public abstract void addMenuView(Menu menu);
    public abstract void onMenuSelected(int id);
    public abstract void initBottomView(ViewGroup viewGroup);
}
