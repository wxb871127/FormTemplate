package template.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import template.com.form.R;

/**
 *    导航栏的表单TemplateView
 */
public class NavigationTemplateView extends HorizontalScrollView {
    private Context context;
    private int screenWidth;//屏幕宽度
    private int rightPadding;//右内边距
    private int menuWidth;
    private TemplateView contentView;
    private NavigationMenuView menuView;
    private boolean measure = false;

    public NavigationTemplateView(Context context) {
        this(context, null);
        LayoutInflater.from(context).inflate(R.layout.template_drawer,this);
    }

    public NavigationTemplateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationTemplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SliddingMenu, defStyleAttr, 0);
        typedArray.length();
        for(int i=0; i<typedArray.length(); i++){
            int attr = typedArray.getIndex(i);
            if(attr == R.styleable.SliddingMenu_rigthtPadding){
                rightPadding = typedArray.getDimensionPixelSize(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
            }
            break;
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!measure) {
            contentView = findViewById(R.id.template_list);
            menuView = findViewById(R.id.drawer_menu);
            contentView.initTemplate("gxy_pg.xml");
            menuWidth = menuView.getLayoutParams().width = screenWidth - rightPadding;
            contentView.getLayoutParams().width = screenWidth;
            measure = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed)//初始状态contentViwe在屏幕右侧
            smoothScrollTo(menuView.getLayoutParams().width, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                if(getScaleX() >= menuWidth)
                    smoothScrollTo(menuWidth, 0);
                else
                    scrollTo(0,0 );
                break;
        }
        return super.onTouchEvent(ev);
    }
}
