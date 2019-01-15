package template.widget;

import android.app.Activity;
import android.view.ViewGroup;

public interface TemplateViewInterface {
    void setTheme(int theme);//设置主题
    void setDecoratorView(ViewGroup viewGroup, Activity activity);//设置装饰
}
