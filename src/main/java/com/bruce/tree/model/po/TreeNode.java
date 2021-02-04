package com.bruce.tree.model.po;

import lombok.Data;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2021/2/4 21:44
 * @Author Bruce
 */
@Data
public class TreeNode {

    private Long id;

    private Long pId;

    private String code;

    private String name;

}
