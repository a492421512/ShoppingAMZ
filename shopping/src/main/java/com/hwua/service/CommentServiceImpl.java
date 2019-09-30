package com.hwua.service;



import com.hwua.entity.Comment;
import com.hwua.mapper.CommentDao;
import com.hwua.service.I.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    /**
     * 查询所有留言
     * @return
     * @throws SQLException
     */
    @Override
    public List<Comment> QueryAllComment() throws SQLException {
        return commentDao.QueryAllComment();
    }

    @Override
    public Integer addComment(Comment comment) throws SQLException {
        return commentDao.addComment(comment);
    }

    /**
     * 分页查询
     * @return
     * @throws SQLException
     */
    @Override
    public List<Comment> querypageComment() throws SQLException {
        return commentDao.querypageComment();
    }

    /**  @Override
     * 查询留言个数
     * @return
     * @throws SQLException
     */
    public Long queryCount() throws SQLException {
        return commentDao.queryCount();
    }

}
