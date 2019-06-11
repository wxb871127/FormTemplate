package template.control;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.lang.exception.ExceptionUtils;

import java.math.BigDecimal;
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
    protected void handleException(BaseTemplate template, Object object,BaseViewHolder holder) {
        HashMap<String, Object> map = new HashMap<>();
        if(object instanceof BigDecimal){
            map.put (template.name, ((BigDecimal) object).doubleValue());
        }else
            map.put(template.name, object);
        try {
            if(template.editable != null && ExpressionUtil.getExpressionUtil().logicExpression(template.editable,  map, false)){
                view.setException(true);
            }else
                super.handleException(template, object, holder);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
