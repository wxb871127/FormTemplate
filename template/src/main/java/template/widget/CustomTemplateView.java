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
        return CUSTOM_TYPE;
    }

    @Override
    protected int getSpinnerLayout() {
        return TemplateConfig.getCustomLayout(Integer.parseInt(template.command));
    }
}
