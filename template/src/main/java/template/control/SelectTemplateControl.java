package template.control;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import base.annotation.Template;
import base.util.ExpressionUtil;
import template.bean.BaseTemplate;
import template.bean.Item;
import template.bean.RadioTemplate;
import template.bean.SelectTemplate;
import template.widget.BaseTemplateView;
import template.widget.SelectTemplateView;
import template.widget.dialog.BaseTemplateDialog;
import template.widget.dialog.RadioTemplateDialog;
import template.widget.dialog.SelectTemplateDialog;

@Template(tag = "select")
public class SelectTemplateControl<T extends BaseTemplate> extends BaseTemplateControl{
    @Override
    public Class<? extends BaseTemplate> getTemplateClass() {
        return SelectTemplate.class;
    }

    @Override
    public BaseTemplateView getTemplateView(Context context) {
        this.context = context;
        return new SelectTemplateView(context);
    }

    @Override
    public BaseTemplateDialog getDialog(Context context, BaseTemplate template) {
        SelectTemplateDialog selectTemplateDialog = new SelectTemplateDialog(context);
        List<Item> itemList = new ArrayList<>();
        try {
            Map codeMap = ((RadioTemplate)template).getCodeMap();
            Iterator entries = codeMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                Item item = (Item) entry.getValue();
                if(item.show != null && !TextUtils.isEmpty(item.show)){
                    boolean ret = ExpressionUtil.getExpressionUtil().logicExpression(item.show, valueMap, true);
                    if(ret)
                        itemList.add(item);
                }else
                    itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((RadioTemplate) template).setShowItem(itemList);
        return selectTemplateDialog;
    }
}
