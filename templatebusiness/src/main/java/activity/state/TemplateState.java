package activity.state;

import android.content.Context;
import android.view.Menu;
import android.view.ViewGroup;

import com.business.annotation.State;
import com.business.callback.CommandCallback;
import com.business.command.CommandManager;
import com.business.command.bean.CommandMsg;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

import java.util.Map;

import template.interfaces.OnTemplateCommandListener;
import template.view.TemplateView;


public abstract class TemplateState {
    protected TemplateView templateView = null;
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

    protected void setTemplateFlag(){

    }

    public void initContentView(ViewGroup viewGroup){
//        if("0".equals(business.style)){
            templateView = new TemplateView(context);
            viewGroup.addView(templateView);
            templateView.initTemplate(business.res);
//        }else if("1".equals(business.style)){
//            NavigationTemplateView navigationTemplateView = new NavigationTemplateView(context);
//            viewGroup.addView(navigationTemplateView);
//        }else if("2".equals(business.style)){
//            templateView = new TemplateView(context);
//            viewGroup.addView(templateView);
//            templateView.initTemplate(business.res);
//        }
        setTemplateFlag();
        templateView.setTemplateListener(new OnTemplateCommandListener() {
            @Override
            public void onTemplateCommand(final String name, String command) {
                CommandMsg commandmsg = CommandManager.getInstance(context).parseCommand(command);
                if(templateView.getValue(name) != null)
                    commandmsg.params = templateView.getValue(name).toString();

                CommandManager.getInstance(context).execute(commandmsg, new CommandCallback() {
                    @Override
                    public void onFailed(String msg) {

                    }

                    @Override
                    public void onSuccess(Object object) {
                        templateView.setValue(name, object);
                        templateView.notifyData();
                    }
                });
            }
        });

    }

    protected Map<String, Object> getValueMap(){
        return templateView.getValueMap();
    }

    protected JSONObject getValueData(){
        Map<String, Object> map = templateView.getValueMap();
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(map));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void setRes(String res){
        this.business.res = res;
    }

    public abstract void addMenuView(Menu menu);
    public abstract void onMenuSelected(int id);
    public abstract void initBottomView(ViewGroup viewGroup);
}
