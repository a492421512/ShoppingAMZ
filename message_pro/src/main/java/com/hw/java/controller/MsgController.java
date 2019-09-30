package com.hw.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hw.java.domain.Message;
import com.hw.java.domain.User;
import com.hw.java.service.I.MessageService;
import com.hw.java.service.I.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mesS")
public class MsgController {
    @Autowired
    private MessageService ms;
    @Autowired
    private UserService us;

    /**
     * 查看分页信息
     * @param pageNo
     * @param pagSize
     * @return
     */
    @RequestMapping("/show")
    @ResponseBody
    public PageInfo<Message> showMsg(@RequestParam("pageNo")String pageNo, @RequestParam("pageSize") String pagSize, @SessionAttribute("user") User user){
        Integer id = user.getId();
        int pNo = Integer.parseInt(pageNo);
        int pSize = Integer.parseInt(pagSize);
        //调用pageHelper插件的方法进行分页
        PageHelper.startPage(pNo,pSize);
        //查出当前对象的所有短信
        List<Message> list = ms.queryLimitMsg(id);
        PageInfo<Message> pageEntity = new PageInfo<>(list);
        return pageEntity;
    }

    /**
     * 查看具体短信
     * @param id
     * @return
     */
    @RequestMapping("/lookMsg")
    public ModelAndView lookMsg(@RequestParam("id")String id){
        //获取这个短信，传入到作用域
        Message mes = ms.findMessageById(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView();
        mv.addObject("Message",mes);
        mv.setViewName("readMsg");
        return mv;
    }

    /**
     * 提交短信
     * @return
     */
    @RequestMapping("/submit")
    public String submit(@RequestParam("toUser")String name,@RequestParam("title")String title,@RequestParam("content")String content,@SessionAttribute("user")User user){
        User user1 = us.query(name);
        Integer receiveID = user1.getId();
        //获取发送人ID
        Integer sendID = user.getId();
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String msgCreateDate = sdf.format(date);
        //创建短信对象，调用方法传入数据库，默认为1：未读状态
        Message mes = new Message(null,sendID,title,content,1,receiveID,msgCreateDate);
        ms.sendMsg(mes);
        return "redirect:/main.jsp";
    }

    @RequestMapping("/delMsg/{id}")
    public String delMsg(@PathVariable("id")String id){
        //删除短信
        ms.deleteMsgById(Integer.parseInt(id));

        return "redirect:/main.jsp";
    }
}
