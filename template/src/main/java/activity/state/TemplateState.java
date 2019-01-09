package activity.state;

import android.content.Context;
import android.view.Menu;
import android.view.ViewGroup;
import org.w3c.dom.Element;

public abstract class TemplateState {
    enum TemplateBusiness {
        ZX,
        PG,
        SF;
        protected String getData;
        protected String updateData;
        protected String name;
        protected String res;
    }
    TemplateBusiness business;

    public void parseBusiness(Element element){
        business = TemplateBusiness.valueOf(getBusinessType());
        business.getData = element.getAttribute("getData");
        business.name = element.getAttribute("name");
        business.res = element.getAttribute("res");
        business.updateData = element.getAttribute("updateData");
    }

    public String getTemplateName(){
        return business.name;
    }

    public String getTemplateRes(){
        return business.res;
    }

    public abstract String getBusinessType();
    public abstract void addMenuView(Menu menu);
    public abstract void onMenuSelected(int id);
    public abstract void showBottomView(Context context, ViewGroup parent);
}
