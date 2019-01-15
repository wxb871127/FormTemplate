package activity.state;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.lang.reflect.Constructor;

import util.XmlUtil;

public class TemplateStateContext {
    private Context context;
    private TemplateState state;
    private Activity activity;

    public TemplateStateContext(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public TemplateState getTemplateState(){
        return state;
    }

    public void setTemplateState(String templateName){
        try {
            InputStream inputStream = null;
            inputStream = context.getAssets().open("template_business.xml");
            Element rootElement = XmlUtil.getDocumentElement(inputStream);
            NodeList nodeList = rootElement.getElementsByTagName("service");
            for(int i=0; i<nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
                if(templateName.equals(element.getAttribute("name"))){
                    String type = element.getAttribute("type");
                    Class stateClass = Class.forName("activity.state."+type);
                    Constructor constructor = stateClass.getDeclaredConstructor(new Class[]{Context.class, Activity.class});
                    state = (TemplateState)constructor.newInstance(context, activity);
                    state.parseBusiness(element);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Build{



    }
}
