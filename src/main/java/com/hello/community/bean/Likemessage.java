package com.hello.community.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName likemessage
 */
@TableName(value ="likemessage")
@Data
public class Likemessage implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer parentId;

    /**
     * 
     */
    private Integer creator;

    /**
     * 
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}