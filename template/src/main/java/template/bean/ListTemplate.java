package template.bean;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

import base.annotation.Template;
import base.util.TemplateList;
import base.util.TemplateParse;

@Template(tag = "list")
public class ListTemplate extends BaseTemplate{
    public TemplateList templates;//列表内部节点和外面一致 这里可以复用
    public String showArgs;
    public String showFormat;

    @Override
    public void parseElement(Element e) {
        super.parseElement(e);
        templates = TemplateParse.parseElement(e);
    }

    @Override
    public String getShowName(Object object, Context context) {
        if(object == null)
            return "";

        JSONArray data = null;
        String values = "";
        try {
            data = new JSONArray(object.toString());
            if(data.length() == 0)
                return "";
            String[] formats = showFormat.split(",");
            String[] args = showArgs.split(",");
            for(int i=0; i<data.length(); i++){
                JSONObject jsonObject = data.getJSONObject(i);
                String showString = "";
                for(int j=0; j<args.length; j++) {
                    showString += String.format(formats[j], getShowString(context,args[j],jsonObject));
                    if(j != args.length-1)
                        showString += ",";
                }
                if(i != data.length()-1)
                    values += showString + "/";
                else values += showString;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return values;
    }

    //获取showArgs中的参数
    private String getShowString(Context context, String arg, JSONObject jsonObject){
        BaseTemplate template = templates.getTemplate(arg);
        return template.getShowName(jsonObject.opt(arg), context);
    }
}
