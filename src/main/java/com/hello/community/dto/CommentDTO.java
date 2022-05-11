package com.hello.community.dto;
import com.hello.community.bean.User;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    private Long parentId;

    private Integer type;

    private Integer commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private String content;

    private int CommentCount;

    private User user;
}
