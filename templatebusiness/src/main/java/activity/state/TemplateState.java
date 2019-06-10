package activity.state;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Toast;

import com.business.annotation.State;
import com.business.callback.CommandCallback;
import com.business.command.CommandManager;
import com.business.command.bean.CommandMsg;
import com.business.command.commands.ActivityCommand;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

import template.com.templatebusiness.R;
import template.interfaces.OnTemplateCommandListener;
import template.view.NavigationMenuView;
import template.view.NavigationTemplateView;
import template.view.SlidPageTemplateView;
import template.view.TemplateView;

import static util.TemplateParse.getDocumentElement;


public abstract class TemplateState {
    enum TemplateBusiness {
        ZX,
        PG,
        SF,
        TJ;
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
        }else if("1".equals(business.style)){
            NavigationTemplateView navigationTemplateView = new NavigationTemplateView(context);
            viewGroup.addView(navigationTemplateView);
        }else if("2".equals(business.style)){
            templateView = new TemplateView(context);
            viewGroup.addView(templateView);
            templateView.initTemplate(business.res);
        }

        final TemplateView finalTemplateView = templateView;
        templateView.setTemplateListener(new OnTemplateCommandListener() {
            @Override
            public void onTemplateCommand(final String name, String command) {
                CommandMsg commandmsg = CommandManager.getInstance(context).parseCommand(command);
                if(finalTemplateView.getValue(name) != null)
                    commandmsg.params = finalTemplateView.getValue(name).toString();

                CommandManager.getInstance(context).execute(commandmsg, new CommandCallback() {
                    @Override
                    public void onFailed(String msg) {

                    }

                    @Override
                    public void onSuccess(Object object) {
                         finalTemplateView.setValue(name, object);
                         finalTemplateView.notifyData();
                    }
                });
            }
        });

    }

    public void setRes(String res){
        this.business.res = res;
    }

    public abstract void addMenuView(Menu menu);
    public abstract void onMenuSelected(int id);
    public abstract void initBottomView(ViewGroup viewGroup);
}
