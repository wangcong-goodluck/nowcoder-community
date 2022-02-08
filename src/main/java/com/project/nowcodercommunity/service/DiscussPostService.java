package com.project.nowcodercommunity.service;

import com.project.nowcodercommunity.dao.DiscussPostMapper;
import com.project.nowcodercommunity.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    //查询出某页的数据
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    //查询行数的方法
    public int finDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
