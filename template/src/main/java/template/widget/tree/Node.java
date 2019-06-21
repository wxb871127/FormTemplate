package template.widget.tree;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private String id;
    private String pId;//父节点
    private int level;//层级
    private boolean isExpand;//是否展开
    private int iconId;
    private Node parent;//父节点
    private List<Node> children = new ArrayList<Node>();//子节点

    public Node(String id,String pId)
    {
        this.id = id;
        this.pId = pId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getpId()
    {
        return pId;
    }

    public void setpId(String pId)
    {
        this.pId = pId;
    }

    /**
     * 获取层级
     * @return
     */
    public int getLevel()
    {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public boolean isExpand()
    {
        return isExpand;
    }

    /**
     * 设置收缩状态
     * @param expand
     */
    public void setExpand(boolean expand)
    {
        isExpand = expand;
        //设置收缩状态
        if(!isExpand)
        {
            //所有子节点都设置成false
            for(Node node : children)
            {
                node.setExpand(isExpand);
            }
        }
    }

    public int getIconId()
    {
        return iconId;
    }

    public void setIconId(int iconId)
    {
        this.iconId = iconId;
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public List<Node> getChildren()
    {
        return children;
    }

    public void setChildren(List<Node> children)
    {
        this.children = children;
    }

    /**
     * 判断是否是根节点
     */
    public boolean isRoot()
    {
        return parent == null;
    }

    /**
     * 判断父节点的展开状态
     */
    public boolean isParentExpand()
    {
        //根节点
        if(parent == null)
            return false;

        return parent.isExpand();
    }
    /**
     * 是否是叶子结点
     */
    public boolean isLeaf()
    {
        return children.size() == 0;
    }

}
