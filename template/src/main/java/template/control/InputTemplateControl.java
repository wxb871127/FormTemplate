package template.control;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.HashMap;

import base.annotation.Template;
import base.util.ExpressionUtil;
import template.bean.BaseTemplate;
import template.bean.InputTemplate;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
import template.widget.InputTemplateView;

@Template(tag = "input")
public class InputTemplateControl extends BaseTemplateControl{
    private Context context;

    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return InputTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        this.context = context;
        return new InputTemplateView(context);
    }

    @Override
    protected void verifyData(BaseTemplate name, Object object,BaseViewHolder holder) {
        String domain = ((InputTemplate)name).domain;
        if(TextUtils.isEmpty(domain)){
            super.verifyData(name, object, holder);
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(name.name, object.toString());
        try {
            Boolean ret = ExpressionUtil.getExpressionUtil().logicExpression(domain, map, false);
            if(!ret){
                Toast.makeText(context, "输入的数据超出范围，请重新输入", Toast.LENGTH_SHORT).show();
                View view = getTemplateView(context);
                ((BaseTemplateView) view).initView(holder, name, "", true);
            }else
                super.verifyData(name, object, holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
