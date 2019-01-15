package template.widget.decorator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import template.widget.TemplateView;
import template.widget.TemplateViewInterface;

/**
 *  表单装饰抽象类，增加导航栏、主题等修饰功能
 */
public abstract class AbstractTemplateDecorator extends TemplateView implements TemplateViewInterface{
    protected TemplateView templateView;
    protected View mView;

    public AbstractTemplateDecorator(Context context) {
        super(context);
    }

    public AbstractTemplateDecorator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AbstractTemplateDecorator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initView(ViewGroup viewGroup){
        mView =  LayoutInflater.from(mContext).inflate(getLayout(), viewGroup);
    }

    protected abstract int getLayout();
}
