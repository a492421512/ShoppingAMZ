/*
package com.hw.java.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//通过过滤器设置用户权限，必须登陆才能查看其他页面
@WebFilter("/*")
public class AllFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //通过强转获取session作用域
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //获取用户请求的uri
        String uri  = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();
        //设定一个集合，存储不需要过滤的uri
        List<String> uriList = new ArrayList<>();
        uriList.add(request.getContextPath()+"/index.jsp");
        uriList.add(request.getContextPath()+"/register.jsp");
        uriList.add(request.getContextPath()+"/s2");
        uriList.add(request.getContextPath()+"/s1");
        uriList.add("/scripts");
        uriList.add("/css");
        uriList.add("/images");
        //如果请求的路径是指定的,直接放行

        if (uri.contains("/index.jsp") || uri.contains("/register.jsp") || uri.contains("/s2") ||uri.contains("/s1") ||uri.contains("/scripts")||uri.contains("/css")||uri.contains("/images")){
            chain.doFilter(request,response);//放行
        }else{
            //如果是其他页面，判断是否登陆
            HttpSession session = request.getSession(false);
            //如果没有登陆，就跳转到登陆页面
            if (session==null || session.getAttribute("user")==null){
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }else{
                chain.doFilter(request, response);//放行
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
*/
