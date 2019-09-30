package com.hw.java.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.java.service.I.MessageService;
import com.hw.java.service.I.UserService;
import com.hw.java.service.MessageServiceImpl;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/remove")
public class RemoveMesServlet extends HttpServlet {
    private static MessageService ms = null;
    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        ms = (MessageService) servletContext.getAttribute("msgService");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;Charset=utf-8");
        //创建Json对象
        ObjectMapper om = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        String param = request.getParameter("param");
        //确定是要删除短信
        if (param.equals("delMsg")) {
            //获取要删除的短信id
            String id = request.getParameter("id");
            //删除短信
            int i = ms.deleteMsgById(Integer.parseInt(id));
            //判断是否删除成功
            if (i > 0) {
                map.put("success",true);
            } else {
                map.put("success",false);
            }
        }
        //把数据转换成JSON字符串，通过字符输出流写给ajax
        om.writeValue(response.getWriter(),map);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
