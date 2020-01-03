package template.control;

import android.content.Context;
import android.text.TextUtils;

import com.wadata.customexpressionlib.ExpressionHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import base.annotation.Template;
import template.bean.BaseTemplate;
import template.bean.Item;
import template.bean.RadioTemplate;
import template.bean.TemplateValue;
import template.widget.BaseTemplateView;
import template.widget.RadioTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.RadioTemplateDialog;

@Template(tag = "radio")
public class RadioTemplateControl<T extends BaseTemplate> extends BaseTemplateControl{

    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return RadioTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        this.context = context;
        RadioTemplateView radioTemplateView = new RadioTemplateView(context);
        return radioTemplateView;
    }

    @Override
    protected void onClickHolder(BaseTemplate template, TemplateValue templateValue) {
        if(!templateValue.editable) return;
        super.onClickHolder(template, templateValue);
    }

    @Override
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        RadioTemplateDialog dialog = new RadioTemplateDialog(context);
        List<Item> itemList = new ArrayList<>();
        try {
            Map codeMap = ((RadioTemplate)template).getCodeMap();
            Iterator entries = codeMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                Item item = (Item) entry.getValue();
                if(item.show != null && !TextUtils.isEmpty(item.show)){
                    boolean ret = ExpressionHelper.getExpressionUtil().logicExpression(item.show, valueMap, true);
                    if(ret)
                        itemList.add(item);
                }else
                    itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((RadioTemplate) template).setShowItem(itemList);
        return dialog;
    }
}
