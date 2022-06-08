package com.hello.community.controller;

import com.hello.community.bean.Likemessage;
import com.hello.community.bean.User;
import com.hello.community.dto.LikeMessageDTO;
import com.hello.community.dto.ResultDTO;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.service.LikemessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LikeCountController {
    @Autowired
    private LikemessageService likemessageService;

    @ResponseBody
    @PostMapping("/like")
    public Object post(@RequestBody LikeMessageDTO likeMessageDTO,
                       HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(likeMessageDTO == null){
            return ResultDTO.errorOf(CustomizeErrorCode.LIKECOUNT_FILE);
        }
        Likemessage likemessage = new Likemessage();
        likemessage.setParentId(likeMessageDTO.getParentId());
        likemessage.setType(likeMessageDTO.getType());
        likemessage.setCreator(user.getId());
        likemessageService.saveOrDeleteMessage(likemessage);
        return ResultDTO.successOf();
    }

}
