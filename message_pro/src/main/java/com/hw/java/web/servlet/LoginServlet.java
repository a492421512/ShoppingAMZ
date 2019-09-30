package com.hw.java.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.java.domain.User;
import com.hw.java.service.I.UserService;
import com.hw.java.service.UserServiceImpl;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/s2"})

public class LoginServlet extends HttpServlet {
    private static UserService uDI;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        uDI = (UserService)servletContext.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map = new HashMap<>();
        //创建JSON对象
        ObjectMapper mapper = new ObjectMapper();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String param = req.getParameter("param");
        if (param.equals("zhuCe")) {
            //获得注册参数
            String name = req.getParameter("username");//用户名
            String psw = req.getParameter("pwd");//密码
            String affirm = req.getParameter("affirm");//确认密码
            String email = req.getParameter("email");//邮箱
            User user = new User();
            user.setName(name);
            user.setPassword(psw);
            user.setEmail(email);
            //判断用户输入是否正确
            Integer test = test(user, affirm);

            if (test >0){
                map.put("success",false);
                map.put("info","输入不合法！请重置重新输入！");
                mapper.writeValue(resp.getWriter(),map);
                return;
            }
            //调用方法判断用户是否存在
            boolean b = uDI.register(user);

            if (b) {
                //注册成功
                map.put("success",true);
                map.put("info","");
            } else {
                //注册失败
                map.put("success",false);
                map.put("info","注册失败！");
            }
            //把map转换为json传递数据
            mapper.writeValue(resp.getWriter(),map);
        }else if (param.equals("queryName")) {//如果是要查询用户名
            String username = req.getParameter("username");
            PrintWriter out = resp.getWriter();
            //调用方法查询用户信息
            User user = uDI.query(username);
            //不等于null就说明用户名已使用
            if (user != null) {
                map.put("success",false);
                map.put("info","该用户名已存在！");
            } else if (user == null && !username.equals("")){
                map.put("success",true);
                map.put("info","恭喜您！该用户名可以使用！");

            }
            //把map转换为json传递数据
            mapper.writeValue(resp.getWriter(),map);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    //判断用户输入的信息是否正确
    private static int test(User user,String affirm){
        String reg="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,3}$";
        if (user.getName().equals("")){
            return 1;
        }else if (user.getPassword().equals("")){
            return 2;
        }else if (!user.getPassword().equals(affirm)) {
            System.out.println("3");
            return 3;
        }else if (user.getEmail().equals("")){
            System.out.println("4");
            return 4;
        }else if (user.getEmail().matches(reg)==false){
            System.out.println("5");
            return 5;
        }
       return -1;
    }

}
