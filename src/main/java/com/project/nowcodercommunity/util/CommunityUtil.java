package com.project.nowcodercommunity.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    //生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密，对密码进行加密
    //特点：只能加密不能解密，(每次加密的结果都是一个值)
    public static String md5(String key) {
        if (StringUtils.isNotBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
