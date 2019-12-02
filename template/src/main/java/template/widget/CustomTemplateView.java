package template.widget;

import android.content.Context;
import template.bean.Attr;
import template.bean.CustomTemplate;

public class CustomTemplateView extends BaseTemplateView<CustomTemplate>{
    public CustomTemplateView(Context context) {
        super(context);
    }

    @Override
    public int getType() {
        return -1;
    }

    @Override
    public int getlayout() {
        return -1;
    }

    @Override
    public void initView(BaseViewHolder holder, CustomTemplate template, Object value, Attr attr) {

    }
}
