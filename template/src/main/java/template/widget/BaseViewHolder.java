package template.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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

    public void setOnClickListener(int viewId, View.OnClickListener listener){
        View view = itemView.findViewById(viewId);
        view.setOnClickListener(listener);
    }

    public void setOnClickListener(View.OnClickListener listener){
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
}
