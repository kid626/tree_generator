package com.bruce.tree.util;

import com.bruce.tree.model.vo.TreeNodeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2021/2/4 21:44
 * @Author Bruce
 */
public class TreeUtil {

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @param root      根节点 id
     * @param <T>       继承 TreeNodeVo 的子类
     * @return 树结构
     */
    public static <T extends TreeNodeVo<T>> List<T> build(List<T> treeNodes, Long root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getPId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getPId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
            treeNode.hasChild();
        }
        return trees;
    }


    /**
     * 使用递归方法建树
     *
     * @param treeNodes 传入的树节点列表
     * @return 树结构
     */
    public static <T extends TreeNodeVo<T>> List<T> buildByRecursive(List<T> treeNodes, Long root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getPId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
            treeNode.hasChild();
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes 传入的树节点列表
     * @return 树结构
     */
    private static <T extends TreeNodeVo<T>> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getPId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
            it.hasChild();
        }
        return treeNode;
    }
}
