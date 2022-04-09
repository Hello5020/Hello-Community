package com.hello.community.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户识别的唯一id
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String accountId;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String token;

    /**
     * 
     */
    private Long gmtCreat;

    /**
     * 
     */
    private Long gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}