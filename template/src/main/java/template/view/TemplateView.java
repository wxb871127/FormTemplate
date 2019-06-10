package template.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Map;

import template.bean.BaseTemplate;
import template.bean.TemplateList;
import template.interfaces.OnTemplateCommandListener;
import template.widget.TemplateAdapter;
import util.TemplateParse;

/**
 *  自定义表单View
 */
public class TemplateView extends RecyclerView{
    private TemplateAdapter templateAdapter;
    protected Context mContext;
//    private OnTemplateCommandListener listener;

    public TemplateView(Context context) {
        this(context, null);
    }

    public TemplateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemplateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

//    public void setListener(OnTemplateCommandListener listener){
//        this.listener = listener;
//    }

    public void initTemplate(String templateXml){
        TemplateParse.initTemplateStyle(mContext);
        TemplateList templates = TemplateParse.parseTemplateFile(mContext, templateXml);
        initTemplate(templates);
    }

    public void initTemplate(TemplateList templates){
        setItemAnimator(null);//防止刷新recyckerView焦点丢失问题
        templateAdapter = new TemplateAdapter(mContext, templates);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        setLayoutManager(layoutManager);
        setAdapter(templateAdapter);
//        templateAdapter.setListener(new OnTemplateCommandListener() {
//            @Override
//            public void onTemplateCommand(String name, String command) {
//                if(listener != null)
//                    listener.onTemplateCommand(name, command);
//            }
//        });
    }

    public TemplateList getTemplateList(){
        return templateAdapter.getTemplateList();
    }

    public void setTemplateListener(final OnTemplateCommandListener listener){
        templateAdapter.setListener(new OnTemplateCommandListener() {
            @Override
            public void onTemplateCommand(String name, String command) {
                listener.onTemplateCommand(name, command);
            }
        });

    }

    public void setEditMode(boolean edit){
        templateAdapter.setEditMode(edit);
    }

    public <T extends Object> void setValue(String key, T value){
        templateAdapter.putValue(key, value);
    }

    public <T extends Object> T getValue(String key){
        return templateAdapter.getValue(key);
    }

    public Map<String, Object> getValueMap(){
        return templateAdapter.valueMap;
    }

    public void notifyData(){
        templateAdapter.notifyDataSetChanged();
    }
}
