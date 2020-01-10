package template.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.annotation.AttrTemplate;
import base.annotation.Template;
import base.util.ReflectUtil;
import base.util.TemplateList;
import template.bean.BaseTemplate;
import template.bean.CustomTemplate;
import template.bean.SectionTemplate;
import template.bean.TemplateValue;
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
    public Map<String, TemplateValue> valueMap;//表单数据
    public Map<String, Object> codeMap;
    private boolean editMode = true;//整张表单是否可编辑状态, 该状态优先级大于字段的editable
    private OnTemplateCommandListener listener;
    private int mFlag = 0x0;
    private Map<String, Boolean> manual;//手动触发时，异常属性值不重新计算

    public TemplateAdapter(Context context) {
        this(context, new HashMap<String, TemplateValue>());
    }

    TemplateAdapter(Context context, Map<String, TemplateValue> outMap) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        valueMap = new HashMap<>();
        manual = new HashMap<>();
        codeMap = new HashMap<>();
        this.valueMap.putAll(outMap);
        setHasStableIds(true);//防止刷新recyckerView焦点丢失问题
    }

    public void init(TemplateList templates) {
        TemplateConfig.initCustomView();
        ArrayList list = templates;
        setDatas(list);
        setLevel(2);
        initSetting();
        this.templates = templates;
        for (BaseTemplate template : templates) {
            if (template instanceof SectionTemplate) continue;
            valueMap.put(template.name, new TemplateValue(null, false, false, true));
            codeMap.put(template.name, null);
        }
    }

    public void setListener(OnTemplateCommandListener listener) {
        this.listener = listener;
    }

    public void setTemplateFlag(int flag) {
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
        if (templateControl != null) {
            if (templateControl instanceof CustomTemplateControl)
                return Integer.parseInt(((CustomTemplate) (templates.get(position))).command);
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
        templates.get(position).position = position;
        final BaseTemplateControl templateControl = getTemplateControl(templates.get(position));
        ((BaseViewHolder) holder).setFlag(mFlag);
        //((BaseViewHolder) holder).getConvertView().setPadding(node.getLevel() * 20,1,0,1);
        if (templateControl != null) {
            templateControl.setTemplateListener(new OnTemplateListener() {
                @Override
                public void onDataChanged(BaseTemplate key, Object value, boolean notify) {
                    try {
                        TemplateValue templateValue = valueMap.get(key.name);
                        templateValue.value = value;
                        codeMap.put(key.name, value);
                        valueMap.put(key.name, templateValue);
                        if (notify)
                            notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAttrChanged(BaseTemplate key, String attr, Object value, boolean notify) {
                    TemplateValue templateValue = valueMap.get(key.name);
                    Field[] fields = ReflectUtil.findFieldByAnnotation(templateValue.getClass(), AttrTemplate.class);
                    if ("exception".equals(attr) && !(Boolean) value) {
                        templateValue.exceptionDesc = "";
                    }
                    for (Field field : fields) {
                        if (field != null) {
                            AttrTemplate attrTemplate = field.getAnnotation(AttrTemplate.class);
                            if (attr.equals(attrTemplate.attr())) {
                                try {
                                    field.set(templateValue, value);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                    manual.put(key.name, (Boolean) value);
                    if (notify)
                        notifyDataSetChanged();
                }

                @Override
                public void onDatasChanged(Map<String, Object> map, boolean notify) {
                    for (String key : map.keySet()) {
                        if (valueMap.containsKey(key)) {
                            TemplateValue templateValue = valueMap.get(key);
                            templateValue.value = map.get(key);
                            codeMap.put(key, map.get(key));
                        }
                    }
                    if (notify)
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
            templateControl.initView(context, (BaseViewHolder) holder, node, templates, templateControl.getTemplate(), valueMap, codeMap, editMode, manual);
        }
    }

    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    private void notifyExpressionData() {
        for (BaseTemplate template : templates) {
            if (template.expression) {
                notifyItemChanged(template.position);
            }
        }
    }

    private BaseTemplateControl getTemplateControl(BaseTemplate template) {
        Template tp = template.getClass().getAnnotation(Template.class);
        BaseTemplateControl templateControl = null;
        templateControl = TemplateConfig.getTemplateControlByTag(tp.tag());
        templateControl.setTemplate(template);
        return templateControl;
    }

    public TemplateList getTemplateList() {
        return templates;
    }

    private View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    public void putValue(String key, TemplateValue value) {
        valueMap.put(key, value);
        codeMap.put(key, value.value);
    }


    public Object getValue(String key) {
        return valueMap.get(key);
    }

    public void addValueMap(Map<String, TemplateValue> outMap) {
        this.valueMap.putAll(outMap);
        for (String key : outMap.keySet()) {
            codeMap.put(key, outMap.get(key).value);
        }
    }

    public void setValueMap(Map<String, TemplateValue> map) {
        this.valueMap.clear();
        this.valueMap = map;
    }

    public void setEditMode(boolean edit) {
        editMode = edit;
        notifyDataSetChanged();
    }

    public boolean getEditMode() {
        return editMode;
    }

    public void setCommandValue(String command, Map map) {
        if (templates.isEmpty()) return;
        if (TextUtils.isEmpty(command)) throw new IllegalArgumentException("command can not null");
        BaseTemplate template = templates.getTemplateByCommand(command);
        TemplateValue templateValue = valueMap.get(template.name);
        templateValue.showValue = map.get("show").toString();
        templateValue.exception = Boolean.parseBoolean(String.valueOf(map.get("exception")));
        templateValue.value = map.get("value");
        valueMap.put(template.name, templateValue);
        codeMap.put(template.name, map.get("value"));
        notifyDataSetChanged();
    }

    public void setDataSource(Map map) {
        for (Object key : map.keySet()) {
            setDataSource(key.toString(), map.get(key));
        }
    }

    public void setDataSource(String dataSource, Object value) {
        for (BaseTemplate template : templates) {
            if (dataSource.equals(template.dataSource)) {
                TemplateValue templateValue = valueMap.get(template.name);
                if (templateValue == null) {
                    templateValue = new TemplateValue();
                    templateValue.value = value;
                    valueMap.put(template.name, templateValue);
                } else {
                    templateValue.value = value;
                }
            }
        }
    }

    public boolean checkRequired() {
        for (BaseTemplate template : templates) {
            if ("true".equals(template.required)) {
                TemplateValue templateValue = valueMap.get(template.name);
                if (templateValue.value == null || TextUtils.isEmpty(templateValue.value.toString())) {
                    Toast.makeText(context, "必填项" + template.label + "未填写", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }
}
