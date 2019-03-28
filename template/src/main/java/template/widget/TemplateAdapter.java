package template.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;
import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.TemplateList;
import template.config.TemplateConfig;
import template.control.BaseTemplateControl;
import util.StringUtil;

public class TemplateAdapter extends BaseTemplateAdapter {
    protected TemplateList templates;
    protected Context context;
    protected LayoutInflater mLayoutInflater;
    public Map<String, Object> valueMap;//表单数据
    private boolean editMode = true;//整张表单是否可编辑状态, 该状态优先级大于字段的editable

    public TemplateAdapter(Context context, TemplateList templates){// List<BaseTemplateControl> templates) {
        this(context, templates, new HashMap<String, Object>());
    }

    TemplateAdapter(Context context, TemplateList templates, Map<String, Object> outMap) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.templates = templates;
        valueMap = new HashMap<>();
        this.valueMap.putAll(outMap);
        setHasStableIds(true);//防止刷新recyckerView焦点丢失问题
    }

    @Override
    protected BaseViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        int id = TemplateConfig.getTemplateLayoutByType(viewType);
        return new BaseViewHolder(getItemView(id, parent));
    }

    @Override
    public int getItemViewType(int position) {
        BaseTemplateControl templateControl = getTemplateControl(templates.get(position));
        if(templateControl != null)
            return templateControl.getTemplateView(context).getType();
        return 0;
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    @Override
    public long getItemId(int position) {//防止刷新recyckerView焦点丢失问题
        return position;
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BaseTemplateControl templateControl = getTemplateControl(templates.get(position));
        if(templateControl != null)
            templateControl.initView(context, (BaseViewHolder) holder, templateControl.getTemplate(), valueMap, editMode);
        templateControl.setTemplateListener(new BaseTemplateControl.OnTemplateListener() {
            @Override
            public void onTemplateUpdate(String key, Object value) {
                valueMap.put(key, value);
//                if(getItemViewType(position) != 1)
                    notifyDataSetChanged();
            }
        });
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

    public <T extends Object> void putValue(String key, T value){
        valueMap.put(key, value);
    }

    public <T extends Object> T getValue(String key){
        return (T) valueMap.get(key);
    }

    public void addValueMap(Map<String, Object> outMap){
        this.valueMap.putAll(outMap);
    }

    public void setValueMap(Map<String, Object> map){
        this.valueMap.clear();
        this.valueMap = map;
    }

    public void setEditMode(boolean edit){
        editMode = edit;
        notifyDataSetChanged();
    }
}
