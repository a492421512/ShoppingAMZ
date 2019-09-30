package com.hw.java.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.java.domain.User;
import com.hw.java.service.I.UserService;
import com.hw.java.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*@WebServlet({"/s1"})*/
public class MyServlet extends HttpServlet {

    private static UserService uSI ;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        uSI = (UserService)servletContext.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String param = req.getParameter("param");
        //创建集合，放入数据
        Map<String,Object> map =new HashMap<>();
        //如果指令是登陆
        if (param.equals("login")) {
            //查询用户是否存在
            String username = req.getParameter("name");
            String psw = req.getParameter("pwd");
            User user = new User();
            user.setName(username);
            user.setPassword(psw);
            user = uSI.login(user);

            if (user != null) {

                //把用户信息传入到作用域
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                map.put("success",true);
                map.put("info","");
                //重定向到操作短信的Servlet
                //resp.sendRedirect(req.getContextPath() + "/mesS?param=queryAllMes&id=" + user.getId());
            } else {

                //用户不存在，直接打印通过ajax传递
                map.put("success",false);
                map.put("info","用户名密码错误");
            }

            //把map转换成JSON
            ObjectMapper mapper = new ObjectMapper();
            //通过输出流传过去JSON
            mapper.writeValue(resp.getWriter(),map);
        }
    }
}
