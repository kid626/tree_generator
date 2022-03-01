package com.bruce.tree.service.impl;

import com.bruce.tree.model.convert.TreeNodeConvert;
import com.bruce.tree.model.form.TreeNodeForm;
import com.bruce.tree.model.po.TreeNode;
import com.bruce.tree.model.vo.TreeNodeBaseVo;
import com.bruce.tree.service.TreeNodeCacheService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2022/3/1 10:55
 * @Author fzh
 */
public class TreeNodeCacheBaserServiceImpl extends TreeNodeCacheService<TreeNode, TreeNodeForm, TreeNodeBaseVo> {

    @Override
    public TreeNodeConvert<TreeNode, TreeNodeForm, TreeNodeBaseVo> generateConvert() {
        return new TreeNodeConvert<>(new TreeNode(), new TreeNodeForm(), new TreeNodeBaseVo());
    }

    @Override
    public TreeNode queryById(Long id) {
        return new TreeNode();
    }

    @Override
    public TreeNode queryByCode(String code) {
        return new TreeNode();
    }

    @Override
    public List<TreeNode> queryByPid(Long pId) {
        return new ArrayList<>();
    }

    @Override
    public List<TreeNode> getAll() {
        return new ArrayList<>();
    }

    @Override
    public List<TreeNode> queryLikeCode(String code) {
        return new ArrayList<>();
    }

    @Override
    public long insert(TreeNode treeNode) {
        return 0;
    }

    @Override
    public long updateEntity(TreeNode treeNode) {
        return 0;
    }

    @Override
    public TreeNode initRoot() {
        TreeNode root = new TreeNode();
        root.setId(getParentId());
        root.setPId(-1L);
        root.setName("root");
        root.setCode(getPCode());
        this.insert(root);
        return root;
    }


    private String getPCode() {
        StringBuilder sb = new StringBuilder("1");
        int codeSize = getCodeSize();
        while (sb.length() < codeSize) {
            sb.append("0");
        }
        return sb.toString();
    }

}
