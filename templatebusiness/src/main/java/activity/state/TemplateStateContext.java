package activity.state;

import android.content.Context;

import com.business.annotation.State;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class TemplateStateContext {
    private Context context;
    private TemplateState state;
    private List<Class> stateClassList = new ArrayList<>();

    public TemplateStateContext(Context context){
        this.context = context;
        stateClassList.add(PGTemplateState.class);
        stateClassList.add(ZXTemplateState.class);
        stateClassList.add(SFTemplateState.class);
        stateClassList.add(TJTemplateState.class);
    }

    public Class getStateClassByType(String type){
        for(Class cls : stateClassList){
            State state = (State)cls.getAnnotation(State.class);
            if(type.equalsIgnoreCase(state.state())){
                return cls;
            }
        }
        return null;
    }

    public TemplateState getTemplateState(){
        return state;
    }

    public void setTemplateState(String templateName){
        try {
            InputStream inputStream = null;
            inputStream = context.getAssets().open("template_business.xml");
            Element rootElement = getDocumentElement(inputStream);
            NodeList nodeList = rootElement.getElementsByTagName("service");
            for(int i=0; i<nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
                if(templateName.equals(element.getAttribute("name"))){
                    String type = element.getAttribute("type");
                    Class stateClass = getStateClassByType(type);
                    Constructor constructor = stateClass.getDeclaredConstructor(new Class[]{Context.class});
                    state = (TemplateState)constructor.newInstance(context);
                    state.parseBusiness(element);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Element getDocumentElement(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            return doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class Build{



    }
}
