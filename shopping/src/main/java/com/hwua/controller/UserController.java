package com.hwua.controller;

import com.hwua.entity.ShopCart;
import com.hwua.entity.User;
import com.hwua.service.I.ShopCartService;
import com.hwua.service.I.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private  UserService userSvc;
    @Autowired
    private  ShopCartService shopcarSvs;
    // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
    private String regID = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
    //邮箱正则表达式
    private String regEm="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,3}$";
    //手机号正则表达式
    private String regNum = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";


    //用户登录
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(User user, @RequestParam("code")String code, HttpSession session){

        Map<String,Object> map = new HashMap<>();
        //判断用户是否存在
        try {
            user = userSvc.login(user.getUname(),user.getPwd());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //判断验证码是否正确
        String checkCode = (String) session.getAttribute("checkCode");
        System.out.println(checkCode);

        if (user==null){ //假设用户不存在
            map.put("success",false);
            map.put("error","账号密码错误！");
        }else if (!code.equals(checkCode)){ //如果验证码不正确
            map.put("success",false);
            map.put("error","验证码错误");
        }else if (user!=null && code.equals(checkCode)) {//如果账号密码验证码都正确则通过
            map.put("success", true);
            //把用户信息放入session
            session.setAttribute("user", user);
            try {
                //查询登陆之前是否有加入购物车
                List<ShopCart> shopCarts = shopcarSvs.showCart(0L);
                if (shopCarts != null) {
                    //就把虚拟Uid改为当前用户id
                    shopcarSvs.updateUid(user.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    //用户注册
    @ResponseBody
    @RequestMapping("register")
    public Map<String,Object> register(User user,@RequestParam("confirm")String confirm,@RequestParam("code")String code,HttpSession session){
        System.out.println(user+confirm+code);
        //获取验证码
        String checkCode = (String) session.getAttribute("checkCode");
        System.out.println(checkCode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*Date date = new Date();
        try {
            date = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //转换成sql.Date
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        //设置正常类型的生日
        user.setBirthday(date1);*/

        Map<String,Object> map = new HashMap<>();
        if (user.getUname().equals("")){
            map.put("success",false);
            map.put("error","用户名不能为空！");
        }else if (user.getPwd().equals("")){
            map.put("success",false);
            map.put("error","密码不能为空！");
        }else if (confirm.equals("")){
            map.put("success",false);
            map.put("error","确认密码不能为空！");
        }else if (!user.getPwd().equals(confirm)){
            map.put("success",false);
            map.put("error","两次密码不相同！");
        } else if (user.getSex().equals("")){
            map.put("success",false);
            map.put("error","请选择性别！");
        }else if (user.getBirthday().equals("")){
            map.put("success",false);
            map.put("error","生日不能为空！");
        }else if (user.getIdcard().equals("")){
            map.put("success",false);
            map.put("error","请输入身份证号");
        }else if (user.getIdcard().matches(regID)==false){
            map.put("success",false);
            map.put("error","请输入正确的身份证号");
        }else if (user.getEmail().equals("")){
            map.put("success",false);
            map.put("error","邮箱不能为空！");
        }else if (user.getEmail().matches(regEm)==false){
            map.put("success",false);
            map.put("error","请输入正确邮箱！列如：xxx@xx.com");
        }else if (user.getMobile().equals("")){
            map.put("success",false);
            map.put("error","手机号不能为空！");
        }else if (user.getMobile().matches(regNum)==false){
            map.put("success",false);
            map.put("error","请输入正确的手机号");
        }else if (user.getAddress().equals("")){
            map.put("success",false);
            map.put("error","地址不能为空！");
        }else if (code.equals("")){
            map.put("success",false);
            map.put("error","验证码不能为空！");
        }else if (!code.equals(checkCode)){
            map.put("success",false);
            map.put("error","验证码不正确！");
        }else {//否则进行登陆操作
            try {
                boolean register = userSvc.register(user);
                //判断是否登陆成功
                if (register){
                    map.put("success",true);
                }else {
                    map.put("success",false);
                    map.put("error","登陆失败！");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(map);
        return map;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
