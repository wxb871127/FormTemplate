package template.widget.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import template.widget.tree.annotation.TreeNodeId;
import template.widget.tree.annotation.TreeNodeParentId;

public class TreeViewHelper {
    public static final int ICON_NONE = - 1;
    public static final int CURRENT_LEVEL = 1;

    public static <T> List<Node> convertDatasToNodes(List<T> datas) throws IllegalAccessException
    {
        List<Node> nodes = new ArrayList<>();
        for(T t : datas) {
            Class clazz = t.getClass();
            Node node = getNode(clazz, t);
            nodes.add(node);
        }
        settingNodeRelation(nodes);
//        settingNodeIcon(nodes);
        return nodes;
    }

    private static <T> Node getNode(Class clazz, T t) throws IllegalAccessException{
        if(clazz.getName().equals("java.lang.Object"))
            return new Node("", "");
        Field[] fields = clazz.getDeclaredFields();
        String id = "";
        String pId = "";
        boolean findId = false;
        boolean findPid = false;
        for(Field field : fields) {
            if(field.getAnnotation(TreeNodeId.class) != null) {
                field.setAccessible(true);
                id = field.get(t).toString();
                findId = true;
            }
            if(field.getAnnotation(TreeNodeParentId.class) != null) {
                field.setAccessible(true);
                pId = field.get(t).toString();
                findPid = true;
            }
            if(findId && findPid)
                return new Node(id, pId);
        }
        return getNode(clazz.getSuperclass(), t);
    }

    /**
     * 设置节点之间的关系
     * @param nodes
     */
    private static void settingNodeRelation(List<Node> nodes)
    {
        //设置关系
        for(int i = 0;i < nodes.size();i++) {
            Node n = nodes.get(i);
            for(int j = i + 1;j < nodes.size();j++) {
                Node m = nodes.get(j);
                //n的父节点的id等于m节点的id;也就是m节点是n的父节点
                if(n.getpId() == m.getId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                } else if(m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                }
            }
        }
    }
    /**
     * 设置图标状态
     * @param nodes
     */
    private static void settingNodeIcon(List<Node> nodes)
    {
        for(Node no : nodes) {
            setNodeIcon(no);
        }
    }
    /**
     * 设置节点图标
     *
     * @param node
     */
    public static void setNodeIcon(Node node)
    {
        //有子节点
        if(node.getChildren().size() > 0) {
            //展开
            if(node.isExpand()) {
//                node.setIconId(R.mipmap.icon_expand);
                return;
            }
            //收缩
//            node.setIconId(R.mipmap.icon_collapse);

        }
        //没有图标
        else
            node.setIconId(ICON_NONE);
    }

    /**
     * 获取排序后的节点
     *
     * @param datas
     * @param <T>
     * @return
     */
    public static <T> List<Node> getSortedNodes(List<T> datas,int defaultLevel) throws IllegalAccessException
    {
        List<Node> newNodes = new ArrayList<>();
        //未排序的节点,只是转换的
        List<Node> oldNodes = convertDatasToNodes(datas);
        /**
         * 类似于采用深度遍历树
         * PS:另外的方法，前序遍历树
         */
        //获取根节点
        List<Node> rootNodes = getRootNodes(oldNodes);
        for(Node node : rootNodes) {
            //默认级别为1
            addNode(newNodes,node,defaultLevel,CURRENT_LEVEL);
        }
        return newNodes;
    }

    /**
     * 深度遍历，并添加
     *
     * @param newNodes
     * @param node
     * @param defaultLevel
     * @param currentLevel
     */
    private static void addNode(List<Node> newNodes,Node node,int defaultLevel,int currentLevel)
    {
        //添加节点
        newNodes.add(node);
        //设置展开的级别
        if(defaultLevel >= currentLevel) {
            node.setExpand(true);
        }
        //叶子节点，也就是最后了，不用再遍历
        if(node.isLeaf())
            return;
        //遍历子节点
        for(int i = 0;i < node.getChildren().size();i++) {
            addNode(newNodes,node.getChildren().get(i),defaultLevel,currentLevel + 1);
        }

    }

    /**
     * 过滤出需要显示的节点
     */
    public static List<Node> filterVisibleNodes(List<Node> nodes)
    {

        List<Node> visibleNodes = new ArrayList<>();

        for(Node node : nodes) {
            if(node.isRoot() || node.isParentExpand()) {
                //设置节点的图标
                setNodeIcon(node);

                visibleNodes.add(node);
            }
        }

        return visibleNodes;
    }

    /**
     * 获取根节点
     *
     * @param oldNodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> oldNodes)
    {
        List<Node> rootNode = new ArrayList<>();
        for(Node node : oldNodes) {
            if(node.isRoot()) {
                rootNode.add(node);
            }
        }
        return rootNode;
    }
}
