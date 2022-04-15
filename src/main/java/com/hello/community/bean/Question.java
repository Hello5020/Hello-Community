package com.hello.community.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Long gmtCreate;

    /**
     * 
     */
    private Long gmtModifie;

    /**
     * 
     */
    private Integer creator;

    /**
     * 
     */
    private Integer commentCount;

    /**
     * 
     */
    private Integer viewCount;

    /**
     * 
     */
    private Integer likeCount;

    /**
     * 
     */
    private String tag;

    /**
     * 
     */
    @Version
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}