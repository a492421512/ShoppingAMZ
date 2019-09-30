package com.hw.java.web.servlet;

import com.hw.java.domain.User;
import com.hw.java.service.I.MessageService;
import com.hw.java.service.I.UserService;
import com.hw.java.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/*@WebServlet("/userS")*/

public class UserServlet extends HttpServlet {

    private UserService us ;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        us = (UserService)servletContext.getAttribute("userService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //判断操作
        String param = request.getParameter("param");
        //发送短信：查询所有的用户
        if (param.equals("queryUserAll")){
            //调用查询用户方法得到结果集
            List<User> list = us.getAllUsers();
            //传入到作用域
            request.setAttribute("userList",list);
            //转发到发送短信页面
            request.getRequestDispatcher("/newMsg.jsp").forward(request,response);

            //退出：如果收到退出命令
        }else if (param.equals("exists")){
            //获取session
            HttpSession session = request.getSession(false);
            session.removeAttribute("user");
            //退出之后需要销毁，User数据
            session.invalidate();

            //返回到登陆页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            //回信：根据id查询出指定用户
        }else if (param.equals("queryUserById")){
            //获取发送人的id
            String sendId = request.getParameter("sendId");
            System.out.println(sendId);
            //获取发送人的信息，传入到作用域
            User userId = us.findUserById(Integer.parseInt(sendId));
            System.out.println(userId);
            request.setAttribute("sendUser",userId);
            //跳转到发送信息页面
            request.getRequestDispatcher("/newMsg.jsp").forward(request,response);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
