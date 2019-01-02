package template.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class BaseTemplateAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    protected abstract BaseViewHolder getItemViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);
}
