package com.project.nowcodercommunity.controller;

import com.project.nowcodercommunity.entity.User;
import com.project.nowcodercommunity.service.UserService;
import com.project.nowcodercommunity.util.CommunityConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {

    @Autowired
    private UserService userService;

    //访问注册页面的请求
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    //访问登录页面
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    //处理注册的请求
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {//注册成功
            //注册成功，跳转到首页
            model.addAttribute("msg", "注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    //处理邮件激活的请求
    // http://localhost:8080/community/activation/101/code 访问路径不能随便写，要按照这样拼
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        if (result == ACTIVATION_SUCCESS) {//成功
            model.addAttribute("msg", "激活成功，您的账号已经可以正常使用");
            model.addAttribute("target", "/login");//跳转到登录页面
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效的操作，该账户已经激活过");
            model.addAttribute("target", "/index");//跳转到首页
        } else {
            model.addAttribute("msg", "激活失败，您提供的激活码不正确");
            model.addAttribute("target", "/index");//跳转到首页
        }
        return "/site/operate-result";
    }
}
