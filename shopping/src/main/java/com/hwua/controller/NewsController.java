package com.hwua.controller;

import com.hwua.entity.News;
import com.hwua.service.I.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewService newSvc ;

    //查询所有新闻
    @RequestMapping("/queryNews")
    @ResponseBody
    public List<News> queryNews(){

        //通过查询获得新闻结果集
        List<News> list = null;
        try {
            list = newSvc.queryNews();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    //通过id查询新闻
    @ResponseBody
    @RequestMapping("/queryNewById")
    public News queryNewById(@RequestParam("id")Integer id){
        News news = new News();
        try {
            news = newSvc.queryNewById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
    }
}
