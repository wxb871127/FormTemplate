package template.widget;

import android.content.Context;
import android.view.View;

import template.bean.SearchTemplate;
import template.bean.TemplateValue;

public class SearchTemplateView extends BaseTemplateView<SearchTemplate>{
    public SearchTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return SEARCH_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, SearchTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, template, value);
        if(value.editable){
            text.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.setText(value.showValue);
        }else {
            text.setVisibility(View.VISIBLE);
            editText.setVisibility(View.INVISIBLE);
            text.setText(value.showValue);
        }
    }
}
