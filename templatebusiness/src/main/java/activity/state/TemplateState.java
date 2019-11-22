package activity.state;

import android.content.Context;
import android.view.Menu;
import android.view.ViewGroup;

import com.business.annotation.State;
import com.business.callback.CommandCallback;
import com.business.command.CommandManager;
import com.business.command.bean.CommandMsg;
import com.convert.Converter;
import com.convert.TemplateConverterFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.Set;

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
        protected String edit;//初始化是否可编辑
        protected String res;//对应的表单xml文件
        protected String style;//0 不带装饰 1带有导航栏 2左右滑动页 3带导航的左右滑动页
    }
    TemplateBusiness business;
    protected Context context;
    protected Converter.Factory factory;
    private JSONObject jsonObject;

    public TemplateState(Context context){
        this.context = context;
        factory = new TemplateConverterFactory();
    }

    public void parseBusiness(Element element){
        State state= this.getClass().getAnnotation(State.class);
        business = TemplateBusiness.valueOf(state.state());
        business.getData = element.getAttribute("getData");
        business.name = element.getAttribute("name");
        business.res = element.getAttribute("res");
        business.updateData = element.getAttribute("updateData");
        business.style = element.getAttribute("style");
        business.edit = element.getAttribute("edit");
    }

    public String getTemplateName(){
        return business.name;
    }

    public String getTemplateRes(){
        return business.res;
    }

    protected void setTemplateFlag(){

    }

    public void setConverterFactory(Converter.Factory factory){
        this.factory = factory;
    }

    public void setTemplateData(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public void initContentView(ViewGroup viewGroup){
        templateView = new TemplateView(context);
        viewGroup.addView(templateView);
        templateView.initTemplate(business.res);
        templateView.setEditMode(Boolean.parseBoolean(business.edit));

        if(jsonObject != null) {
            Map map = (Map<String, Object>) factory.inputConverter().convert(jsonObject);
            templateView.setValue(map);
            if(factory.attrInputConverter() != null) {
                Map attrMap = (Map<String, Object>) factory.attrInputConverter().convert(jsonObject);
                if (attrMap != null)
                    templateView.setAttrValue(attrMap);
            }
        }
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
        Map<String, Object> attrMap = templateView.getAttrValueMap();
        JSONObject jsonObject = factory.outputConverter().convert(map,attrMap);
        return jsonObject;
    }

    public void setRes(String res){
        this.business.res = res;
    }

    public abstract void addMenuView(Menu menu);
    public abstract void onMenuSelected(int id);
    public abstract void initBottomView(ViewGroup viewGroup);
}
