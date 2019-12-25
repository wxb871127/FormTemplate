package template.widget;

import android.content.Context;
import template.bean.CustomTemplate;
import template.config.TemplateConfig;

public class CustomTemplateView extends BaseTemplateView<CustomTemplate>{
    public CustomTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return TemplateConfig.CUSTOM_TYPE;
    }

    @Override
    protected int getSpinnerLayout() {
        return TemplateConfig.getCustomSpinnerLayout(Integer.parseInt(template.command));
    }

    @Override
    public int getContentLayout() {
        return TemplateConfig.getCustomContentLayout(Integer.parseInt(template.command));
    }
}
