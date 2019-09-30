package com.hwua.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwua.entity.Comment;
import com.hwua.service.I.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentDao;

    //留言分页
    @RequestMapping("/queryCom")
    @ResponseBody
    public PageInfo<Comment> queryCom(Integer pagesize,Integer currentPage){
        Map<String,Object> map = new HashMap<>();
        List<Comment> comList = null;
        //使用插件进行分页
        PageHelper.startPage(currentPage,pagesize);
        try {
            /*获取每页内容*/
            comList = commentDao.querypageComment();
            /*  comList = commentDao*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PageInfo<Comment> page = new PageInfo(comList);
        return page;
    }

    //添加留言
    @RequestMapping("/addCom")
    public String addCom(Comment comment){
        System.out.println(comment+"start");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);/*将创建时间字符串化*/
        comment.setCreate_Time(format);
        comment.setReply_Time(format);
        System.out.println(comment);
        try {
            commentDao.addComment(comment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/guestbook.jsp";
    }
}
