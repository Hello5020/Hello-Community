package com.hello.community.dto;

import com.hello.community.bean.User;
import lombok.Data;

@Data
public class QuestionDTO {
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

    private User user;

}
