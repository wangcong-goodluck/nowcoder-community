package com.project.nowcodercommunity.dao;


import com.project.nowcodercommunity.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectById(int id);//根据id查询用户

    User selectByName(String username);//根据用户名查user

    User selectByEmail(String email);//根据邮箱查user

    int insertUser(User user);//增加一个用户

    int updateStatus(int id, int status);//对user的状态修改

    int updateHeader(int id, String headerUrl);//更新头像的路径

    int updatePassword(int id, String password);//更新密码


}
