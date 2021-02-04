package com.bruce.tree.service;

import com.bruce.tree.model.vo.TreeNodeVo;

import java.util.List;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2021/2/4 21:44
 * @Author Bruce
 */
public interface BaseService<T, F, V extends TreeNodeVo<V>> {

    /**
     * 新增
     *
     * @param form TreeForm
     * @return 主键
     */
    long save(F form);

    /**
     * 修改
     *
     * @param form TreeForm
     * @return 更新条数
     */
    long update(F form);

    /**
     * 根据主键获取
     *
     * @param id 主键
     * @return 返回一个或者为 null
     */
    T queryById(Long id);

    /**
     * 根据 code 获取
     *
     * @param code code
     * @return 返回一个或者为 null
     */
    T queryByCode(String code);

    /**
     * 根据 父 id 获取
     *
     * @param pId 父 id
     * @return 可能会有多个
     */
    List<T> queryByPid(Long pId);

    /**
     * 生成树
     *
     * @return TreeVo
     */
    V tree();


    /**
     * 获取根节点，第一次进来新增
     *
     * @return 根节点
     */
    T getRoot();

    /**
     * 获取当前节点到根节点的路径
     *
     * @param id 某个节点主键
     * @return 返回该节点到根节点的路径
     */
    List<T> getRootPath(Long id);


    /**
     * 获取当前节点及其所有子节点
     * 根据 code 来，code 做右模糊查询
     *
     * @param id 某个节点主键
     * @return 返回该节点及其所欲子节点
     */
    List<T> getChildNode(Long id);

    /**
     * 获取全部
     *
     * @return 全部组织信息
     */
    List<T> getAll();

    /**
     * 类型转换
     *
     * @param list 实体类 po list
     * @return vo list
     */
    List<V> convert2VoList(List<T> list);

    /**
     * 类型转换
     *
     * @param t 实体类 po
     * @return vo
     */
    V convert2Vo(T t);

    /**
     * 类型转换
     *
     * @param f form
     * @return 实体类po
     */
    T convert2Po(F f);

    /**
     * 根据 code 模糊查询，右模糊查询
     *
     * @param code 编码
     * @return 统一返回一个 list
     */
    List<T> queryLikeCode(String code);

    /**
     * 新增
     *
     * @param t 实体类对象
     * @return 主键
     */
    long insert(T t);

    /**
     * 更新
     *
     * @param t 实体类对象
     * @return 更新条数
     */
    long updateEntity(T t);

    /**
     * 生成根节点
     *
     * @return 根节点
     */
    T initRoot();

}
