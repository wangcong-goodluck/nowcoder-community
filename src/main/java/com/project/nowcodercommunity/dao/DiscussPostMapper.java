package com.project.nowcodercommunity.dao;

import com.project.nowcodercommunity.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //分页查询帖子的方法
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);//offset每一页起始行的行号，limit每一页最多显示多少条数据

    //查询帖子行数
    //@Param注解用于给参数取别名，如果只有一个参数，并且在<if>里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    //增加帖子的方法
    int insertDiscussPost(DiscussPost discussPost);

    //查询帖子的详情
    DiscussPost selectDiscussPostById(int id);

    //更新帖子评论数量
    int updateCommentCount(int id, int commentCount);

}
