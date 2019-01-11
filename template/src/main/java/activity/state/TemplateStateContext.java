package activity.state;

import android.content.Context;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import util.XmlUtil;

public class TemplateStateContext {
    private Context context;
    private TemplateState state;

    public TemplateStateContext(Context context){
        this.context = context;
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
                    state = (TemplateState) Class.forName("activity.state." + type).newInstance();
                    state.parseBusiness(element);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
