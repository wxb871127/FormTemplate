package template.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.TemplateList;
import template.config.TemplateConfig;
import template.control.BaseTemplateControl;
import util.TemplateParse;

/**
 *  自定义表单View
 */
public class TemplateView extends RecyclerView {
    private TemplateAdapter templateAdapter;
    private Context mContext;

    public TemplateView(Context context) {
        super(context);
        mContext = context;
    }

    public TemplateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public TemplateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initTemplate(String templateXml){
        TemplateParse.initTemplateStyle(mContext);
        TemplateList templates = TemplateParse.parseTemplateFile(mContext, templateXml);
        initTemplate(templates);
    }

    public void initTemplate(TemplateList templates){
        templateAdapter = new TemplateAdapter(mContext, templates);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        setLayoutManager(layoutManager);
        setAdapter(templateAdapter);
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
