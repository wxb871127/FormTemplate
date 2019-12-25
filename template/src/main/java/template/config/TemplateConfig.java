package template.config;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import base.annotation.Template;
import template.bean.BaseTemplate;
import template.control.BaseTemplateControl;
import template.control.ButtonTemplateControl;
import template.control.CustomTemplateControl;
import template.control.DateTemplateControl;
import template.control.InputTemplateControl;
import template.control.ListTemplateControl;
import template.control.RadioTemplateControl;
import template.control.SearchTemplateControl;
import template.control.SectionTemplateControl;
import template.control.SelectTemplateControl;
import template.widget.BaseTemplateView;
import template.widget.CustomTemplateView;

public class TemplateConfig {
    public static final int SECTION_TYPE = 0;
    public static final int INPUT_TYPE = 1;
    public static final int RADIO_TYPE = 2;
    public static final int SELECT_TYPE = 3;
    public static final int DATE_TYPE = 4;
    public static final int SEARCH_TYPE = 5;
    public static final int LIST_TYPE = 6;
    public static final int BUTTON_TYPE = 7;
    public static final int CUSTOM_TYPE = -1;

    private static final List<TemplateInfo> templateInfoList = new ArrayList<>();
    private static class TemplateInfo{
        String tag;
        int type;
        int layout;
        Class<? extends BaseTemplate> template;
        BaseTemplateControl templateControl;
    }
    private static Map<Integer, Class> customMap = new HashMap();
    private static Map<Integer, CustomView> customViewMap = new HashMap<>();
    private static Context mContext;

    /*
        扫描被注解为Template的class
        表单支持的展示类型、xml的标签名、Template类的注解一致
     */
    public static void initConfig(Context context){
        mContext = context;
        List<Class> list = new ArrayList<>();
        list.add(SectionTemplateControl.class);
        list.add(InputTemplateControl.class);
        list.add(RadioTemplateControl.class);
        list.add(SelectTemplateControl.class);
        list.add(DateTemplateControl.class);
        list.add(SearchTemplateControl.class);
        list.add(ButtonTemplateControl.class);
        list.add(ListTemplateControl.class);
        list.add(CustomTemplateControl.class);

        for(Class cl : list){
            try {
                TemplateInfo templateInfo = new TemplateInfo();
                Template t = (Template) cl.getAnnotation(Template.class);
                templateInfo.tag = t.tag();
                templateInfo.templateControl = (BaseTemplateControl) cl.newInstance();

                Method method = cl.getMethod("getTemplateView", Context.class);
                BaseTemplateView templateView = (BaseTemplateView)method.invoke(cl.newInstance(), context);
                templateInfo.layout = templateView.getlayout();
                templateInfo.type = templateView.getType();

                method = cl.getMethod("getTemplateClass", null);
                Class<? extends BaseTemplate>  aClass = (Class<? extends BaseTemplate>) method.invoke(cl.newInstance(),null);
                templateInfo.template = aClass;
                templateInfoList.add(templateInfo);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public static Class<? extends BaseTemplate> getTemplateByTag(String tag){
        for(TemplateInfo templateInfo : templateInfoList){
            if(tag.equals(templateInfo.tag))
                return templateInfo.template;
        }
        return null;
    }

    public static BaseTemplateControl getTemplateControlByTag(String tag){
        for(TemplateInfo templateInfo : templateInfoList){
            if(tag.equals(templateInfo.tag))
                return templateInfo.templateControl;
        }
        return null;
    }

    public static int getTemplateLayoutByType(int type){
        int layout = -1;
        for(TemplateInfo templateInfo : templateInfoList){
            if(type == templateInfo.type) {
                layout = templateInfo.layout;
                break;
            }
        }
        if(layout == -1)
            return getCustomLayout(type);
        return layout;
    }

    public static void initCustomView(){
        customViewMap.clear();
        for(Integer index : customMap.keySet()){
            try {
                customViewMap.put(index, (CustomView) customMap.get(index).newInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerCustomView(String command, Class clas){
        customMap.put(Integer.valueOf(command), clas);
    }

    public static int getCustomLayout(int command){
        int layout = customViewMap.get(command).getLayout();
        if(layout != -1) return layout;
        return new CustomTemplateView(mContext).getlayout();
    }

    public static int getCustomContentLayout(int command){
        return customViewMap.get(command).getContentLayout();
    }

    public static int getCustomSpinnerLayout(int command){
        return customViewMap.get(command).getSpinnerLayout();
    }

    public static CustomView getCustomView(int command){
        return customViewMap.get(command);
    }
}
