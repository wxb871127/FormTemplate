package template.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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
}
