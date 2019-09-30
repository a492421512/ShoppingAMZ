package com.hw.java.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hw.java.domain.Message;
import com.hw.java.domain.User;
import com.hw.java.service.I.MessageService;
import com.hw.java.service.I.UserService;
import com.hw.java.service.MessageServiceImpl;
import com.hw.java.service.UserServiceImpl;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*@WebServlet("/mesS")*/

public class MessageServlet extends HttpServlet {
    private static MessageService ms = null;
    private static UserService us = null;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        us = (UserService)servletContext.getAttribute("userService");
        ms = (MessageService) servletContext.getAttribute("msgService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获得当前用户信息
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute("user");
        //获取指令参数
        String param = request.getParameter("param");
        //如果要查询所有的短信
        if (param.equals("queryAllMes")) {
            //获取用户id
            Integer id = user.getId();
            //获取短消息
            //获取当前用户所有的短消息
            List<Message> list = ms.findMessagesByReceiveid(id);
            //把list传入到作用域
            request.setAttribute("MesList", list);

            //登陆成功跳转到main.html页面
            request.getRequestDispatcher("/main.jsp").forward(request, response);
            //提交短信
        }else if (param.equals("submit")){
            //获取接收人ID
            String name = request.getParameter("toUser");
            User user1 = us.query(name);
            Integer receiveID = user1.getId();
            //获取发送人ID
            Integer sendID = user.getId();
            //获取短信标题
            String title = request.getParameter("title");
            //获取短信内容
            String content = request.getParameter("content");
            //获取当前时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String msgCreateDate = sdf.format(date);
            //创建短信对象，调用方法传入数据库，默认为1：未读状态
            Message mes = new Message(null,sendID,title,content,1,receiveID,msgCreateDate);
            int i = ms.sendMsg(mes);
            if (i>0){
                response.sendRedirect(request.getContextPath()+"/main.jsp");
            }
        }else if (param.equals("lookMes")){//如果是查看短信
            //获取短信ID
            String mesId = request.getParameter("id");
            //获取这个短信，传入到作用域
            Message mes = ms.findMessageById(Integer.parseInt(mesId));
            request.setAttribute("Message",mes);
            //转发到查看页面
            request.getRequestDispatcher("/readMsg.jsp").forward(request,response);

        }else if (param.equals("show")){
            Integer pageNo =Integer.parseInt( request.getParameter("pageNo"));
            Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
            Integer id = user.getId();//获得登陆人的id
            //调用pageHelper插件的方法进行分页
            PageHelper.startPage(pageNo,pageSize);
            //查出当前对象的所有短信
            List<Message> list = ms.queryLimitMsg(id);
            PageInfo<Message> pageEntity = new PageInfo<>(list);
            //把数据通过ajax写入到页面
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(),pageEntity);

        }




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
