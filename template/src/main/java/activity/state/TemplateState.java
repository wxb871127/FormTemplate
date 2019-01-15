package activity.state;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import org.w3c.dom.Element;
import template.com.form.R;
import template.widget.TemplateView;
import template.widget.decorator.TemplateViewDrawerDecor;
import template.widget.decorator.TemplateViewPageDecor;

public abstract class TemplateState {
    enum TemplateBusiness {
        ZX,
        PG,
        SF;
        protected String getData;
        protected String updateData;
        protected String name;
        protected String res;
        protected String style;//0 不带装饰 1 带有导航栏 2左右滑动页 3带导航的左右滑动页
    }
    TemplateBusiness business;
    protected Context context;
    protected Activity activity;
    protected ViewGroup parent;

    public TemplateState(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void parseBusiness(Element element){
        business = TemplateBusiness.valueOf(getBusinessType());
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

    /**
     * 根据不同需求配置不同的装饰TemplateView
     */
    public void initContentView(){
        parent = (ViewGroup) LayoutInflater.from(context).inflate(getBusinessLayout(), null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        TemplateView templateView = null;
        if("0".equalsIgnoreCase(business.style)){//未装饰的TemplateView
            templateView = new TemplateView(context);
            parent.addView(templateView, layoutParams);
        }else if("1".equalsIgnoreCase(business.style)){//带导航栏的TemplateView
            templateView = new TemplateViewDrawerDecor(context);
            templateView.setDecoratorView(parent, activity);
        }else if("2".equalsIgnoreCase(business.style)){//带滑动页的TemplateView
            templateView = new TemplateViewPageDecor(context);
            parent.addView(templateView, layoutParams);
        }else if("3".equalsIgnoreCase(business.style)){
            templateView = new TemplateViewDrawerDecor(context);
            templateView.setDecoratorView(new TemplateViewPageDecor(context), null);
        }
        initTemplateView(templateView);
    }

    public int getBusinessLayout(){
        if("1".equalsIgnoreCase(business.style)){
            return R.layout.template_drawer_layout;
        }else
            return R.layout.template_normal_layout;
    }

    public abstract String getBusinessType();
    public abstract void addMenuView(Menu menu);
    public abstract void onMenuSelected(int id);
    public abstract void initBottomView();
    public abstract void initTemplateView(TemplateView templateView);
}
