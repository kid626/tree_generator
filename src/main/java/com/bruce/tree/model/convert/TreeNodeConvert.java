package com.bruce.tree.model.convert;

import com.bruce.tree.model.form.TreeNodeForm;
import com.bruce.tree.model.po.TreeNode;
import com.bruce.tree.model.vo.TreeNodeVo;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2021/2/4 21:44
 * @Author Bruce
 */
public class TreeNodeConvert<T extends TreeNode, F extends TreeNodeForm, V extends TreeNodeVo<V>> {

    private T t;

    private F f;

    private V v;

    public TreeNodeConvert(T t, F f, V v) {
        this.t = t;
        this.f = f;
        this.v = v;
    }

    public T getT() {
        return t;
    }

    public F getF() {
        return f;
    }

    public V getV() {
        return v;
    }


    public V convert2Vo(T tree) {
        V vo = getV();
        vo.setCode(tree.getCode());
        vo.setId(tree.getId());
        vo.setPId(tree.getPId());
        vo.setName(tree.getName());
        return vo;
    }

    public T convert2Po(F form) {
        T t = getT();
        t.setId(form.getId());
        t.setPId(form.getPId());
        t.setName(form.getName());
        return t;
    }

}
