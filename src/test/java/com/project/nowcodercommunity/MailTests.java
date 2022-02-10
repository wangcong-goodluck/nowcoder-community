package com.project.nowcodercommunity;

import com.project.nowcodercommunity.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NowcoderCommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {

        mailClient.sendMail("wangcong_goodluck@163.com", "Test", "welcome");
    }

    @Test
    public void testHtmlMail() {

        Context context = new Context();
        context.setVariable("username", "sunday");

        //模板负责格式化，不负责发送
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        //发送邮件
        mailClient.sendMail("wangcong_goodluck@163.com", "Html", content);
    }

}
