package com.project.nowcodercommunity.service;

import com.project.nowcodercommunity.dao.UserMapper;
import com.project.nowcodercommunity.entity.User;
import com.project.nowcodercommunity.util.CommunityConstant;
import com.project.nowcodercommunity.util.CommunityUtil;
import com.project.nowcodercommunity.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sun.dc.pr.PRError;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;//注入邮件的客户端

    @Autowired
    private TemplateEngine templateEngine;//注入模板引擎

    @Value("${community.path.domain}")//注入域名
    private String domain;

    @Value("${server.servlet.context-path}")//注入项目名
    private String contextPath;


    //根据用户id查询用户
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    //注册
    public Map<String, Object> register(User user) {

        Map<String, Object> map = new HashMap<>();
        //对空值进行判断处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        if (StringUtils.isBlank(user.getUsername())) {//账号为空，
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {//密码为空，
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {//邮箱为空，
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }

        //验证账号是否已被注册
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg","该账号已存在");
            return map;
        }
        //验证邮箱是否已被注册
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }

        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));//设置随机值，截取前5位
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));//设置密码，加密密码
        user.setType(0);//注册类型，0：默认都是普通用户
        user.setStatus(0);//注册状态，0：默认都是未激活状态
        user.setActivationCode(CommunityUtil.generateUUID());//激活码
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));//设置随机头像
        userMapper.insertUser(user);//添加到库里

        //利用模板发邮件，激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());//发给用户的邮件
        // http://localhost:8080/community/activation/101/code 要求激活的路径是这样的
        String url = domain + contextPath + "/activation" + user.getId() + "/" + user.getActivationCode();//动态拼路径
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);//利用模板引擎生成邮件的内容
        mailClient.sendMail(user.getEmail(), "激活账号", content);//邮件发送给用户的邮箱

        return map;
    }

    //激活邮件
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {//已经激活过
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {//激活码和传入的激活码是一样的，激活成功
            userMapper.updateStatus(userId, 1);//激活成功后，要把用户的状态改成1
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }


}
