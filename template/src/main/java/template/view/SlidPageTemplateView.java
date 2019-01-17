package template.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import template.com.form.R;

/**
 *  滑动页的表单TemplateView
 */
public class SlidPageTemplateView extends ViewPager{
    private Context context;
    private boolean measure;
    private TemplateView basicView;
    private TemplateView contentView;


    public SlidPageTemplateView(@NonNull Context context) {
        super(context);
        this.context = context;
        setAdapter(new Adapter());
    }

    public SlidPageTemplateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!measure){
            addView(basicView = new TemplateView(context));
            addView(contentView = new TemplateView(context));
            basicView.initTemplate("gxy_zx.xml");
            contentView.initTemplate("gxy_zx.xml");
            measure = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public class Adapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }
    }
}
