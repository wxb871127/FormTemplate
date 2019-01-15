package template.widget.decorator;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 *   ViewPage的装饰
 */
public class TemplateViewPageDecor extends AbstractTemplateDecorator{
    public TemplateViewPageDecor(Context context) {
        super(context);
    }

    public TemplateViewPageDecor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TemplateViewPageDecor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void setTheme(int theme) {

    }

    @Override
    public void setDecoratorView(ViewGroup viewGroup, Activity activity) {
    }
}
