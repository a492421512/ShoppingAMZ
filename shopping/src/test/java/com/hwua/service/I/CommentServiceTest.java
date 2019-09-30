package com.hwua.service.I;

import com.hwua.entity.Comment;
import com.hwua.mapper.CommentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CommentServiceTest {
    @Autowired
    private CommentDao dao;
    @Autowired
    private CommentService commentService;
    @Test
    public void queryAllComment() throws SQLException {
        List<Comment> comments = dao.QueryAllComment();
        System.out.println(comments);
    }

    @Test
    public void addComment() throws SQLException {
        Comment com = new Comment();
        com.setContent("123");
        com.setNick_Name("213");
        Integer integer = dao.addComment(com);
        System.out.println(integer);

    }

    @Test
    public void querypageComment() throws SQLException {
        System.out.println(dao);
        List<Comment> comments = dao.querypageComment();
        System.out.println(comments);
    }

    @Test
    public void queryCount() throws SQLException {
        Comment comment = dao.queryByID(1L);
        System.out.println(comment);
    }
}