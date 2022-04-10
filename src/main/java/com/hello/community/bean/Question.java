package com.hello.community.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}