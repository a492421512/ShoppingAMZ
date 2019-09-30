package com.hwua.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwua.service.I.ProductCategoryService;
import com.hwua.service.I.ProductService;
import com.hwua.service.ProductCategoryServiceImpl;
import com.hwua.service.ProductServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AllFilter implements Filter {


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //设置全局servlet的编码
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(req, resp);
    }



}
