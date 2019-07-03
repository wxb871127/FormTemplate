package template.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import java.util.Map;
import base.util.TemplateList;
import template.interfaces.OnTemplateCommandListener;
import template.widget.TemplateAdapter;
import base.util.TemplateParse;

/**
 *  自定义表单View
 */
public class TemplateView extends RecyclerView{
    public static final int FLAG_EXCEPTION = 0x1;//异常标志
    public static final int FLAG_REFUSE = 0x2;//拒检标志
    private int mFlag = 0x0;
    private TemplateAdapter templateAdapter;
    protected Context mContext;

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

    public void addFlags(int flag){
        mFlag |= flag;
        templateAdapter.setTemplateFlag(mFlag);
    }

    public void setEditMode(boolean edit){
        templateAdapter.setEditMode(edit);
    }

    public void setValue(String key, Object value){
        templateAdapter.putValue(key, value);
    }

    public void setAttrValue(String key, Object value){
        templateAdapter.putAttrValue(key, value);
    }

    public void setValue(Map<String, Object> value){
        templateAdapter.setValueMap(value);
    }

    public void setAttrValue(Map<String, Object> value){
        templateAdapter.setAttrMap(value);
    }

    public Object  getValue(String key){
        return templateAdapter.getValue(key);
    }

    public Object getAttrValue(String key){
        return templateAdapter.getAttrValue(key);
    }

    public Map<String, Object> getValueMap(){
        return templateAdapter.valueMap;
    }

    public Map<String, Object> getAttrValueMap(){
        return templateAdapter.attrMap;
    }

    public void notifyData(){
        templateAdapter.notifyDataSetChanged();
    }
}
