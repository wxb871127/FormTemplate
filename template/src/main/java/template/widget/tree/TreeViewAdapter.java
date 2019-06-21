package template.widget.tree;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

import template.widget.BaseViewHolder;

public abstract class TreeViewAdapter<T> extends RecyclerView.Adapter {
    protected List<Node> mAllNodes;//所有的节点
    protected List<Node> mVisibleNodes;//显示的节点
    private List<T> datas;
    private int level = 0;

    protected void setDatas(List<T> datas){
        this.datas = datas;
    }

    protected void setLevel(int level){
        this.level = level;
    }

    public void initSetting(){
        try {
            mAllNodes = TreeViewHelper.getSortedNodes(datas,level);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mVisibleNodes = TreeViewHelper.filterVisibleNodes(mAllNodes);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Node node = mVisibleNodes.get(position);
        onBindItemViewHolder(holder, position, node);
    }

    @Override
    public int getItemCount() {
        return mVisibleNodes == null ? 0 : mVisibleNodes.size();
    }

    /**
     * 展开或者收缩
     * @param position
     */
    private void expandOrCollapse(int position)
    {
        Node node = mVisibleNodes.get(position);
        if(node != null)
        {
            if(node.isLeaf())
                return;
            //反向选择
            node.setExpand(!node.isExpand());
            //更新数据
            mVisibleNodes = TreeViewHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();

        }
    }

    protected abstract BaseViewHolder getItemViewHolder(ViewGroup parent, int viewType);
    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position, Node node);
}
