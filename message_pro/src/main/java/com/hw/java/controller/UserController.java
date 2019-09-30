package com.hw.java.controller;

import com.hw.java.domain.User;
import com.hw.java.service.I.MessageService;
import com.hw.java.service.I.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userS")
public class UserController {
    @Autowired
    private UserService uSI ;
    @Autowired
    private MessageService msgSV;

    @RequestMapping("/login")
    @ResponseBody
    //@requestParam 参数required ：是否必须带有次参数，默认为true（必须带有）
    public Map<String,Object> login(User user,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user1 = uSI.login(user);

        if (user1 != null) {
            //把用户信息传入到作用域
            session.setAttribute("user", user1);
            map.put("success",true);
            map.put("info","");
            //重定向到操作短信的Servlet
            //resp.sendRedirect(req.getContextPath() + "/mesS?param=queryAllMes&id=" + user.getId());
        } else {

            //用户不存在，直接打印通过ajax传递
            map.put("success",false);
            map.put("info","用户名密码错误");
        }
        return map;
    }

    /**
     * 发送短信：查询所有的用户
     * @return
     */
    @RequestMapping("/queryUserAll")
    public ModelAndView queryUserAll(){
        ModelAndView mv = new ModelAndView();
        //调用查询用户方法得到结果集
        List<User> list = uSI.getAllUsers();
        mv.addObject("userList",list);
        mv.setViewName("newMsg");
        return mv;
    }

    /**
     * 回信：根据id查询出指定用户
     * @param sendId
     * @return
     */
    @RequestMapping("/queryUserById/{sendId}")
    public ModelAndView queryUserById(@PathVariable("sendId")String sendId){
        ModelAndView mv = new ModelAndView();
        User userId = uSI.findUserById(Integer.parseInt(sendId));
        mv.addObject("sendUser",userId);
        mv.setViewName("newMsg");
        return mv;
    }

    /**
     * 退出：如果收到退出命令
     * @return
     */
    @RequestMapping("/exists")
    public String exists(HttpSession session){
        //删除session
        session.removeAttribute("user");
        //退出之后需要销毁，User数据
        session.invalidate();
        return "redirect:/index.jsp";
    }

}
