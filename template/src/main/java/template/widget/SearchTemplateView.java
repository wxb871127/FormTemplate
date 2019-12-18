package template.widget;

import android.content.Context;

import template.bean.SearchTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.widget.tree.Node;

public class SearchTemplateView extends BaseTemplateView<SearchTemplate>{
    public SearchTemplateView(Context context) {
        super(context);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.template_search_content;
    }

    @Override
    protected void initContentView() {

    }

    @Override
    public int getType() {
        return SEARCH_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, Node node, SearchTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, node, template, value);
    }
}
