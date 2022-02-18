package com.project.nowcodercommunity.dao;

import com.project.nowcodercommunity.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 根据实体查询
     * @param entityType 实体类型
     * @param entityId 实体id
     * @param offset 分页
     * @param limit 每页显示行数的限制
     * @return
     */
    List<Comment> selectCommentByEntity(int entityType, int entityId, int offset, int limit);

    //查询数据的条目数
    int selectCountByEntity(int entityType, int entityId);

    //增加评论
    int insertComment(Comment comment);


}
