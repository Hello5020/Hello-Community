package com.hello.community.controller;

import com.hello.community.bean.User;
import com.hello.community.dto.AccessTokenDTO;
import com.hello.community.dto.GithubUser;
import com.hello.community.mapper.UserMapper;
import com.hello.community.provider.GithubProvider;
import com.hello.community.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;
import javax.sql.rowset.serial.SerialStruct;
import java.util.UUID;

/**
 * github登录授权
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callBack(@RequestParam("code")String code,
                           @RequestParam("state")String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.insertOrUpdateUser(user);
            //为登陆成功,获取session和cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,
                         HttpServletResponse response){
        session.removeAttribute("loginUser");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String createUser(User user,
                             @RequestParam("checkPassword")String pwd,
                             Model model,
                             HttpSession session){
        model.addAttribute("name",user.getName());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("check",pwd);
        if (user.getName().isEmpty()) {
            model.addAttribute("msg","用户名不能为空!");
            return checkUser(model);
        }else if (user.getPassword().isEmpty()) {
            model.addAttribute("msg","密码不能为空!");
            return checkUser(model);
        }else if(pwd.isEmpty()){
            model.addAttribute("msg","请确认密码!");
            return checkUser(model);
        }else if (!pwd.equals(user.getPassword())){
            model.addAttribute("msg","确认密码与密码不同!");
            return checkUser(model);
        }
        user.setAccountId(gen());
        userService.insertOrUpdateUser(user);
        session.setAttribute("smsg","注册成功!");
        return "redirect:/login";
    }
    public String gen() {
        UUID uuid = UUID.randomUUID();
        String strUUID = uuid.toString();
        return strUUID;
    }
    @GetMapping("/create")
    public String checkUser(Model model){
        return "signup";
    }

}
