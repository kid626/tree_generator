package com.bruce.tree.service;

import com.bruce.tree.constant.CacheConstant;
import com.bruce.tree.model.convert.TreeNodeConvert;
import com.bruce.tree.model.form.TreeNodeForm;
import com.bruce.tree.model.po.TreeNode;
import com.bruce.tree.model.vo.TreeNodeVo;
import com.bruce.tree.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2021/2/4 21:44
 * @Author Bruce
 */
public abstract class TreeNodeCacheService<T extends TreeNode, F extends TreeNodeForm, V extends TreeNodeVo<V>> implements BaseService<T, F, V> {


    /**
     * 获取类型转换器
     *
     * @return TreeNodeConvert
     */
    public abstract TreeNodeConvert<T, F, V> generateConvert();

    /**
     * 根据主键获取
     *
     * @param id 主键
     * @return 返回一个或者为 null
     */
    @Override
    public abstract T queryById(Long id);

    /**
     * 根据 code 获取
     *
     * @param code code
     * @return 返回一个或者为 null
     */
    @Override
    public abstract T queryByCode(String code);

    /**
     * 根据 父 id 获取
     *
     * @param pId 父 id
     * @return 可能会有多个
     */
    @Override
    public abstract List<T> queryByPid(Long pId);

    /**
     * 获取全部节点
     *
     * @return 所有节点
     */
    @Override
    public abstract List<T> getAll();

    /**
     * 类型转换
     *
     * @param list 实体类 po list
     * @return vo list
     */
    @Override
    public List<V> convert2VoList(List<T> list) {
        List<V> result = new ArrayList<>();
        for (T t : list) {
            V v = generateConvert().convert2Vo(t);
            result.add(v);
        }
        return result;
    }

    /**
     * 类型转换
     *
     * @param t 实体类 po
     * @return vo
     */
    @Override
    public V convert2Vo(T t) {
        return generateConvert().convert2Vo(t);
    }

    /**
     * 类型转换
     *
     * @param f form
     * @return 实体类po
     */
    @Override
    public T convert2Po(F f) {
        return generateConvert().convert2Po(f);
    }

    /**
     * 根据 code 模糊查询，右模糊查询
     *
     * @param code 编码
     * @return 统一返回一个 list
     */
    @Override
    public abstract List<T> queryLikeCode(String code);

    /**
     * 新增
     *
     * @param t 实体类对象
     * @return 主键
     */
    @Override
    public abstract long insert(T t);

    /**
     * 更新
     *
     * @param t 实体类对象
     * @return 更新条数
     */
    @Override
    public abstract long updateEntity(T t);

    /**
     * 生成根节点
     *
     * @return 根节点
     */
    @Override
    public abstract T initRoot();

    /**
     * 新增
     *
     * @param form TreeForm
     * @return 主键
     */
    @Override
    @CacheEvict(value = CacheConstant.TREE, allEntries = true)
    public long save(F form) {
        T t = convert2Po(form);
        String code = generateCode(form.getPId());
        t.setCode(code);
        return insert(t);
    }

    /**
     * 修改
     *
     * @param form TreeForm
     * @return 更新条数
     */
    @Override
    @CacheEvict(value = CacheConstant.TREE, allEntries = true)
    public long update(F form) {
        T t = convert2Po(form);
        // 不修改 pid
        t.setPId(null);
        return updateEntity(t);
    }

    /**
     * 生成树
     *
     * @return TreeVo
     */
    @Override
    @Cacheable(cacheNames = CacheConstant.TREE, key = "'tree'")
    public V tree() {
        List<T> list = getAll();
        List<V> allList = convert2VoList(list);
        List<V> child = TreeUtil.build(allList, getParentId());
        T top = getRoot();
        V v = convert2Vo(top);
        v.setChildren(child);
        v.hasChild();
        return v;
    }


    /**
     * 获取根节点，第一次进来新增
     *
     * @return 根节点
     */
    @Override
    public T getRoot() {
        T root = queryById(getParentId());
        if (root == null) {
            root = initRoot();
        }
        return root;
    }

    /**
     * 获取当前节点到根节点的路径
     *
     * @param id 某个节点主键
     * @return 返回该节点到根节点的路径
     */
    @Override
    public List<T> getRootPath(Long id) {
        List<T> result = new ArrayList<>();
        getRootPath(id, result);
        return result;
    }

    private void getRootPath(Long id, List<T> result) {
        T t = queryById(id);
        if (t != null && !t.getId().equals(getParentId())) {
            result.add(t);
            getRootPath(t.getPId(), result);
        }
    }

    /**
     * 获取当前节点及其所有子节点
     * 根据 code 来，code 做右模糊查询
     *
     * @param id 某个节点主键
     * @return 返回该节点及其所欲子节点
     */
    @Override
    public List<T> getChildNode(Long id) {
        T t = queryById(id);
        return queryLikeCode(t.getCode());
    }

    /**
     * 获取根节点 id
     *
     * @return 根节点 id
     */
    public long getParentId() {
        return 0L;
    }

    /**
     * 获取 code 长度
     *
     * @return code 长度  getCodeBit() * 可能达到的深度
     */
    public int getCodeSize() {
        return 18;
    }

    /**
     * 获取每一层的长度
     *
     * @return 一般 为 3 ，若平级较多可以考虑加大
     */
    public int getCodeBit() {
        return 3;
    }

    /**
     * 生成编码
     *
     * @param parentId 父 id
     * @return code
     */
    private String generateCode(Long parentId) {
        // 表示新增一个顶层的节点
        if (parentId == getParentId()) {
            List<T> list = queryByPid(parentId);
            int num = list.size();
            String code = String.valueOf(101 + num);
            if (code.length() < getCodeSize()) {
                code = StringUtils.rightPad(code, getCodeSize(), '0');
            }
            return code;
        }
        // 找到父节点
        T parentNode = queryById(parentId);
        if (parentNode != null) {
            // 获取父节点的 code
            String code = parentNode.getCode();
            if (StringUtils.isNotEmpty(code)) {
                // 获取父节点前缀
                code = getCodePrefix(code);
                List<T> list = queryLikeCode(code);
                // 获取当前节点下有多少个子节点
                int num = list.size();
                // 需要排除父节点本身
                code = code.concat(String.valueOf((101 + num - 1)));
                if (code.length() < getCodeSize()) {
                    code = StringUtils.rightPad(code, getCodeSize(), '0');
                }
                return code;
            }
        }
        return "";
    }

    /**
     * 获取 code 的前缀
     * 如 101101000000000000 ==> 101101
     * 101000000000000000 ==> 101
     *
     * @param code 编码 code
     * @return 前缀
     */
    private String getCodePrefix(String code) {
        int n = 0;
        for (int i = 0; i < getCodeSize(); i = i + getCodeBit()) {
            if (code.charAt(i) != '0') {
                n++;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getCodeBit() * n; i++) {
            sb.append(code.charAt(i));
        }
        return sb.toString();
    }

}
