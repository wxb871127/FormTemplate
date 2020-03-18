package template.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import template.bean.SectionTemplate;
import template.bean.TemplateValue;
import template.com.form.R;
import template.config.TemplateConfig;
import template.widget.tree.Node;

public class SectionTemplateView extends BaseTemplateView<SectionTemplate> {

    public SectionTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getlayout() {
        return R.layout.template_section;
    }

    @Override
    public int getContentLayout() {
        return 0;
    }

    @Override
    public int getType() {
        return TemplateConfig.SECTION_TYPE;
    }

    @Override
    public void initView(final BaseViewHolder holder, Node node, final SectionTemplate template, final TemplateValue value) {
        TextView label = (TextView) holder.getViewById(R.id.template_label);
        LinearLayout backgroud = (LinearLayout) holder.getViewById(R.id.template_section);
        label.setText(template.label);
//        if(value.editable){
//            backgroud.setBackgroundColor(getResources().getColor(R.color.white));
//        }else
//            backgroud.setBackgroundColor(getResources().getColor(R.color.Pad_Background));
    }
}
