package template.widget;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import template.bean.SearchTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.config.TemplateConfig;
import template.widget.tree.Node;

public class SearchTemplateView extends BaseTemplateView<SearchTemplate>{
    private TextView text;
    private EditText editText;

    public SearchTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getContentLayout() {
        return R.layout.template_search_content;
    }

    @Override
    protected void initContentView() {

    }

    @Override
    public int getType() {
        return TemplateConfig.SEARCH_TYPE;
    }

    @Override
    public void initView(BaseViewHolder holder, Node node, SearchTemplate template, TemplateValue value) {
        holder.getConvertView().setClickable(value.editable);
        super.initView(holder, node, template, value);
        text = (TextView) holder.getViewById(R.id.template_text);
        editText = (EditText) holder.getViewById(R.id.template_edit);

        if(value.editable){
            text.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.setText(value.showValue);
        }else {
            text.setVisibility(View.VISIBLE);
            editText.setVisibility(View.INVISIBLE);
            text.setText(value.showValue);
        }
        setEditableTextColor(text,value.editable,R.color.black);
    }
}
