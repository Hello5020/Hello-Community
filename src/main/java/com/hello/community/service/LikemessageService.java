package com.hello.community.service;

import com.hello.community.bean.Likemessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 25047
* @description 针对表【likemessage】的数据库操作Service
* @createDate 2022-06-08 08:15:05
*/
public interface LikemessageService extends IService<Likemessage> {

    void saveOrDeleteMessage(Likemessage likemessage);
}
