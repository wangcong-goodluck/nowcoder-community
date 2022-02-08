package com.project.nowcodercommunity.service;

import com.project.nowcodercommunity.dao.UserMapper;
import com.project.nowcodercommunity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //根据用户id查询用户
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }
}
