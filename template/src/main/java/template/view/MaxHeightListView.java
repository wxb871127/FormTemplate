package template.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MaxHeightListView extends ListView {
    public MaxHeightListView(Context context) {
        super(context);
    }

    public MaxHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int listViewHeight = -1;

    public int getListViewHeight() {
        return listViewHeight;
    }

    public void setListViewHeight(int listViewHeight) {
        this.listViewHeight = listViewHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (listViewHeight > -1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(listViewHeight,
                    MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
