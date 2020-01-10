package template.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Map;

import base.util.TemplateList;
import template.bean.BaseTemplate;
import template.bean.TemplateValue;
import template.config.CustomView;
import template.config.TemplateConfig;
import template.interfaces.OnTemplateCommandListener;
import template.widget.TemplateAdapter;
import base.util.TemplateParse;

/**
 * 自定义表单View
 */
public class TemplateView extends RecyclerView {
    public static final int FLAG_EXCEPTION = 0x1;//异常标志
    public static final int FLAG_REFUSE = 0x2;//拒检标志
    private int mFlag = 0x0;
    private TemplateAdapter templateAdapter;
    protected Context mContext;
    private LinearLayoutManager layoutManager;

    public TemplateView(Context context) {
        this(context, null);
    }

    public TemplateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemplateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        templateAdapter = new TemplateAdapter(mContext);
    }

    public void initTemplate(String templateXml) {
        TemplateParse.initTemplateStyle(mContext);
        TemplateList templates = TemplateParse.parseTemplateFile(mContext, templateXml);
        initTemplate(templates);
    }

    public void initTemplate(String templateXml, String styleXml) {
        TemplateParse.initTemplateStyle(mContext, styleXml);
        TemplateList templates = TemplateParse.parseTemplateFile(mContext, templateXml);
        initTemplate(templates);
    }

    public void initTemplate(InputStream templateXml, InputStream styleXml) {
        TemplateParse.initTemplateStyle(mContext, styleXml);
        TemplateList templates = TemplateParse.parseStream(templateXml);
        initTemplate(templates);
    }

    public void initTemplate(InputStream templateXml) {
        TemplateParse.initTemplateStyle(mContext);
        TemplateList templates = TemplateParse.parseStream(templateXml);
        initTemplate(templates);
    }

    public void initTemplate(TemplateList templates) {
        if (templates == null)
            throw new IllegalArgumentException("templates is null, please check form xml");
        setItemAnimator(null);//防止刷新recyckerView焦点丢失问题
        templateAdapter.init(templates);
        layoutManager = new LinearLayoutManager(mContext);
        setLayoutManager(layoutManager);
        setAdapter(templateAdapter);
    }

    public TemplateList getTemplateList() {
        return templateAdapter.getTemplateList();
    }

    public void setTemplateListener(final OnTemplateCommandListener listener) {
        templateAdapter.setListener(new OnTemplateCommandListener() {
            @Override
            public void onTemplateCommand(String name, String command) {
                listener.onTemplateCommand(name, command);
            }
        });
    }

    public void addFlags(int flag) {
        mFlag |= flag;
        templateAdapter.setTemplateFlag(mFlag);
    }

    public void setEditMode(boolean edit) {
        templateAdapter.setEditMode(edit);
    }

    public boolean getEditMode() {
        return templateAdapter.getEditMode();
    }

    public void setValue(String key, TemplateValue value) {
        templateAdapter.putValue(key, value);
    }

    public void setCommandValue(String command, Map map) {
        templateAdapter.setCommandValue(command, map);
    }


    public void setValue(Map<String, TemplateValue> value) {
        templateAdapter.setValueMap(value);
    }


    public Object getValue(String key) {
        return templateAdapter.getValue(key);
    }


    public Map<String, TemplateValue> getValueMap() {
        clearFocus();
        templateAdapter.notifyDataSetChanged();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return templateAdapter.valueMap;

    }

    public void notifyData() {
        templateAdapter.notifyDataSetChanged();
    }

    public void setDataSource(String dataSource, Object value) {
        templateAdapter.setDataSource(dataSource, value);
        templateAdapter.notifyDataSetChanged();
    }

    public void setDataSource(Map map) {
        templateAdapter.setDataSource(map);
        templateAdapter.notifyDataSetChanged();
    }

    public boolean checkRequired(boolean navigation) {
        for (BaseTemplate template : templateAdapter.getTemplateList()) {
            TemplateValue templateValue = templateAdapter.valueMap.get(template.name);
            if (templateValue != null && templateValue.refuse != null && templateValue.refuse)
                continue;
            if ("true".equals(template.required)) {
                if (templateValue == null || templateValue.value == null || TextUtils.isEmpty(templateValue.value.toString())) {
                    if (navigation) {
                        moveToPosition(template.position);
                        Toast.makeText(mContext, "必填项" + template.label + "未填写", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkRequired() {
        return checkRequired(true);
    }

    public void moveToPosition(int n) {
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = getChildAt(n - firstItem).getTop();
            scrollBy(0, top);
        } else {
            scrollToPosition(n);
        }
    }
}
