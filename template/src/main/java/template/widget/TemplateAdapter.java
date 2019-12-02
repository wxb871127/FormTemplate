package template.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import base.annotation.AttrTemplate;
import base.annotation.Template;
import base.util.ReflectUtil;
import template.bean.Attr;
import template.bean.BaseTemplate;
import base.util.TemplateList;
import template.bean.CustomTemplate;
import template.bean.SectionTemplate;
import template.config.TemplateConfig;
import template.control.BaseTemplateControl;
import template.control.CustomTemplateControl;
import template.interfaces.OnTemplateCommandListener;
import template.interfaces.OnTemplateListener;
import template.widget.tree.Node;
import template.widget.tree.TreeViewAdapter;

public class TemplateAdapter extends TreeViewAdapter {
    protected TemplateList templates;
    protected Context context;
    protected LayoutInflater mLayoutInflater;
    public Map<String, Object> valueMap;//表单数据
    public Map<String, Object> attrMap;//model属性数据
    private boolean editMode = true;//整张表单是否可编辑状态, 该状态优先级大于字段的editable
    private OnTemplateCommandListener listener;
    private int mFlag = 0x0;
    private Map<String, Boolean> manual;//手动触发时，异常属性值不重新计算

    public TemplateAdapter(Context context){
        this(context, new HashMap<String, Object>());
    }

    TemplateAdapter(Context context, Map<String, Object> outMap){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        valueMap = new HashMap<>();
        attrMap = new HashMap<>();
        manual = new HashMap<>();
        this.valueMap.putAll(outMap);
        setHasStableIds(true);//防止刷新recyckerView焦点丢失问题
    }

    public void init(TemplateList templates){
        ArrayList list = templates;
        setDatas(list);
        setLevel(2);
        initSetting();
        this.templates = templates;
        for(BaseTemplate template : templates){
            if(template instanceof SectionTemplate) continue;
            valueMap.put(template.name, null);
            Field[] fields = ReflectUtil.findFieldByAnnotation(Attr.class, AttrTemplate.class);
            JSONObject jsonObject = new JSONObject();
            for(Field field : fields){
                if(field != null){
                    AttrTemplate attrTemplate = field.getAnnotation(AttrTemplate.class);
                    try {
                        String type = field.getType().getName();
                        if (type.equals("java.lang.Boolean") || type.equals("boolean"))
                            jsonObject.put(attrTemplate.attr(), false);
                        else
                            jsonObject.put(attrTemplate.attr(), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            attrMap.put(template.name, jsonObject);
        }
    }

    public void setListener(OnTemplateCommandListener listener){
        this.listener = listener;
    }

    public void setTemplateFlag(int flag){
        this.mFlag = flag;
    }

    @Override
    protected BaseViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        int id = TemplateConfig.getTemplateLayoutByType(viewType);
        return new BaseViewHolder(getItemView(id, parent));
    }

    @Override
    public int getItemViewType(int position) {
        BaseTemplateControl templateControl = getTemplateControl(templates.get(position));
        if(templateControl != null) {
            if(templateControl instanceof CustomTemplateControl)
                return Integer.parseInt(((CustomTemplate)(templates.get(position))).command);
            else
                return templateControl.getTemplateView(context).getType();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {//防止刷新recyckerView焦点丢失问题
        return position;
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position, final Node node) {
        BaseTemplateControl templateControl = getTemplateControl(templates.get(position));
        ((BaseViewHolder)holder).setFlag(mFlag);
        ((BaseViewHolder) holder).getConvertView().setPadding(node.getLevel() * 30,3,3,3);
        if(templateControl != null) {
            templateControl.initView(context, (BaseViewHolder) holder, templates, templateControl.getTemplate(), valueMap, attrMap, editMode, manual);
            templateControl.setTemplateListener(new OnTemplateListener() {
                @Override
                public void onDataChanged(BaseTemplate key, Object value) {
                    valueMap.put(key.name, value);
//                    manual.clear();
                    try {
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAttrChanged(BaseTemplate key, String attr, Object value) {
                    try {
                        ((JSONObject)attrMap.get(key.name)).put(attr, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if(attr.equals("exception"))
                        manual.put(key.name, (Boolean) value);
                    notifyDataSetChanged();
                }

                @Override
                public void onDatasChanged(Map<String, Object> map) {
                    for(String key : map.keySet()){
                        if(valueMap.containsKey(key))
                            valueMap.put(key, map.get(key));
                    }
//                    manual.clear();
                    notifyDataSetChanged();
                }

            });
            templateControl.setCommandListener(new OnTemplateCommandListener() {
                @Override
                public void onTemplateCommand(String name, String command) {
                    if (listener != null)
                        listener.onTemplateCommand(name, command);
                }
            });
        }
    }

    private BaseTemplateControl getTemplateControl(BaseTemplate template){
        Template tp = template.getClass().getAnnotation(Template.class);
        BaseTemplateControl templateControl = null;
        templateControl = TemplateConfig.getTemplateControlByTag(tp.tag());
        templateControl.setTemplate(template);
        return templateControl;
    }

    public TemplateList getTemplateList(){
        return templates;
    }

    private View getItemView(int layoutResId, ViewGroup parent){
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    public void putValue(String key, Object value){
        valueMap.put(key, value);
    }

    public void putAttrValue(String key, Object value){
        attrMap.put(key, value);
    }

    public Object getValue(String key){
        return  valueMap.get(key);
    }

    public Object getAttrValue(String key) {
        return attrMap.get(key);
    }

    public void addValueMap(Map<String, Object> outMap){
        this.valueMap.putAll(outMap);
    }

    public void addAttrMap(Map<String, Object> map){
        this.attrMap.putAll(map);
    }

    public void setValueMap(Map<String, Object> map){
        this.valueMap.clear();
        this.valueMap = map;
    }

    public void setAttrMap(Map<String, Object> map){
        this.attrMap.clear();
        this.attrMap = map;
    }

    public void setEditMode(boolean edit){
        editMode = edit;
        notifyDataSetChanged();
    }

    public void setCommandValue(String command, Map map){
        if(templates.isEmpty()) return;
        if(TextUtils.isEmpty(command)) throw new IllegalArgumentException("command can not null");
        BaseTemplate template = templates.getTemplateByCommand(command);
        template.hint = map.get("show").toString();
        valueMap.put(template.name, map.get("value"));
        try {
            ((JSONObject)attrMap.get(template.name)).put("exception", map.get("exception"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }
}
