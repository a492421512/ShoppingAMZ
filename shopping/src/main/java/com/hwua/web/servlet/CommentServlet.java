package com.hwua.web.servlet;

import com.alibaba.fastjson.JSON;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hwua.entity.Comment;
import com.hwua.entity.Paging;
import com.hwua.service.I.CommentService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*@WebServlet("/com.do")*/
public class CommentServlet extends HttpServlet {
    @Autowired
    private CommentService commentDao;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String param = request.getParameter("param");

        if (param.equals("queryCom")){//获取所有留言
            List<Comment> comList = null;
            /*获取每页个数*/
            Integer pagesize = Integer.parseInt(request.getParameter("pagesize"));
            /*获取当前页*/
            Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
            //使用插件进行分页
            PageHelper.startPage(currentPage,pagesize);
            try {
                /*获取每页内容*/
                comList = commentDao.querypageComment();
              /*  comList = commentDao*/
            } catch (SQLException e) {
                e.printStackTrace();
            }
            PageInfo page = new PageInfo(comList);
            String str = JSON.toJSONString(page);
            response.getWriter().write(str);
        }else if (param.equals("addCom")){
            String guestName = request.getParameter("guestName");
            String guestTitle = request.getParameter("guestTitle");
            String guestContent = request.getParameter("guestContent");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(date);/*将创建时间字符串化*/
            Comment comment = new Comment(guestTitle, guestContent, format, format, guestName, null);
            System.out.println(comment);
            try {
                Integer row = commentDao.addComment(comment);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath()+"/guestbook.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
