package com.bruce.tree.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2021/2/4 21:44
 * @Author Bruce
 */
@Data
public class TreeNodeForm implements Serializable {

    private Long id;

    private Long pId;

    private String name;

}
