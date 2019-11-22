package template.widget;

import android.content.Context;
import android.widget.TextView;

import template.bean.Attr;
import template.bean.SectionTemplate;
import template.com.form.R;

public class SectionTemplateView extends BaseTemplateView<SectionTemplate> {

    public SectionTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getlayout() {
        return R.layout.section_template;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void initView(final BaseViewHolder holder, final SectionTemplate template, final Object value, Attr attr) {
        TextView label = (TextView) holder.getViewById(R.id.template_label);
        label.setText(template.label);
    }
}
