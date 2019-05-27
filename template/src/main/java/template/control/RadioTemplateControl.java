package template.control;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import base.annotation.Template;
import base.util.ExpressionUtil;
import template.bean.BaseTemplate;
import template.bean.Item;
import template.bean.RadioTemplate;
import template.widget.BaseTemplateView;
import template.widget.BaseViewHolder;
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
        return dialog;
    }

//    @Override
//    protected void verifyData(BaseTemplate name, Object object, BaseViewHolder holder) {
//        super.handleException(name, object, holder);
//
//    }
}
