package template.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import template.com.form.R;

public class BaseViewHolder extends RecyclerView.ViewHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public View getConvertView(){
        return itemView;
    }

    public View getViewById(int id){
        return itemView.findViewById(id);
    }

    public void onClickContent(View.OnClickListener listener){
        setOnClickListener(R.id.common_template_box, listener);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener){
        View view = itemView.findViewById(viewId);
        if(view != null)
            view.setOnClickListener(listener);
    }

    public void setOnClickListener(View.OnClickListener listener){
        if(itemView.isClickable())//列表和输入框 不需要弹出框
            itemView.setOnClickListener(listener);
    }

    public void setShow(boolean show){
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        itemView.setLayoutParams(layoutParams);
        if(!show){
            layoutParams.height = 0;
            layoutParams.width = 0;
            itemView.setVisibility(View.GONE);
        }else {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        }
    }

    //设置标志位 用于判断是否显示属性控件异常、拒检
    public void setFlag(int flag){
        boolean show = false;
        if((flag & 1) != 0x0) {
            View view = getViewById(R.id.template_exception);
            if (view != null) {
                view.setVisibility(View.VISIBLE);
                show = true;
            }
        }
        if((flag & 2) != 0x00) {
            View view = getViewById(R.id.template_refuse);
            if (view != null) {
                view.setVisibility(View.VISIBLE);
                show = true;
            }
        }

        View view = getViewById(R.id.attr);
        if(view == null) return;
        if(show)
            view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }
}
