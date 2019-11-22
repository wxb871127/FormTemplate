package template.widget;

import android.content.Context;
import android.view.View;

import template.bean.Attr;
import template.bean.SearchTemplate;

public class SearchTemplateView extends BaseTemplateView<SearchTemplate>{
    public SearchTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void initView(BaseViewHolder holder, SearchTemplate template, Object value, Attr attr) {
        holder.getConvertView().setClickable(attr.editable);
        super.initView(holder, template, value, attr);
        if(attr.editable){
            text.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.setText(value.toString());
        }else {
            text.setVisibility(View.VISIBLE);
            editText.setVisibility(View.INVISIBLE);
            text.setText(value.toString());
        }
    }
}
