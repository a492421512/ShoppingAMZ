package com.hwua.controller;

import com.hwua.entity.Product;
import com.hwua.entity.ShopCart;
import com.hwua.entity.User;
import com.hwua.service.I.ProductService;
import com.hwua.service.I.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopCar")
public class ShopCarController {
    @Autowired
    private ProductService ps;
    @Autowired
    private ShopCartService cartService;

    //添加购物项
    @RequestMapping("/showCar")
    public String showCar(@RequestParam("id") Integer pid, @RequestParam("num") Integer pnum, @SessionAttribute(value = "user",required = false)User user, RedirectAttributes attr){
        ShopCart shopCart = new ShopCart();
        //判断用户是否登陆
        Long uid = 0L;
        //如果登陆了替换为当前用户id
        if (user!=null){
            uid = user.getId();
        }
        //封装成对象
        shopCart.setPid(pid);
        shopCart.setPnum(pnum);
        shopCart.setUid(uid);
        //获取购物车结果集
        List<ShopCart> shopCarts = null;
        try {
            //添加商品到购物车
            cartService.addProduct(shopCart);
            //获取购物车结果集
            shopCarts = cartService.showCart(uid);
            System.out.println(shopCarts);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //设置到作用域并重定向到购物车页面
        attr.addFlashAttribute("shopCar",shopCarts);
        return "redirect:/shopCar/show";
    }

    //执行 商品数量修改
    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update(Long id, Integer pnum, String type){
        Map<String,Object> map = new HashMap<>();
        if (type.equals("-")) {//减操作
            pnum -= 1;
        }else if (type.equals("+")){//加操作
            pnum += 1;
        }
        try {
            //通过id获取商品信息
            ShopCart updateCart = cartService.queryShopCartById(id);
            Product product = ps.queryProductById(updateCart.getPid());
            //修改的数量不能<=0
            if (pnum<=0){
                map.put("updatesuccess",false);
                map.put("error","操作有误！商品最低数量为1");
                map.put("updatePro",product);
                //把购物项数量改为1
                cartService.updateNum(1, id);
            }else if (pnum >product.getStock()){//修改的数量不能大于库存
                map.put("updatesuccess",false);
                map.put("error","操作有误！商品最大库存为"+product.getStock());
                map.put("updatePro",product);
                //把购物项数量改为最大库存
                cartService.updateNum((int)product.getStock(), id);
            }else {
                //修改商品数量
                cartService.updateNum(pnum, id);
                //获得更改过数量的购物项
                updateCart = cartService.queryShopCartById(id);
                map.put("updatesuccess",true);
                map.put("updateCart", updateCart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    //删除购物项
    @RequestMapping("/delPro")
    public String delPro(Long id,@SessionAttribute(value = "user",required = false)User user, HttpSession session){
        Long uid = 0L;
        //如果登陆了替换为当前用户id
        if (user!=null){
            uid = user.getId();
        }
        try {
            //根据ID删除对应购物项
            cartService.delProduct(id);
            //获取购物车结果集
            List<ShopCart> shopCarts = cartService.showCart(uid);
            //设置到作用域并重定向到购物车页面
            session.setAttribute("shopCar",shopCarts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/shopping.jsp";
    }

    @RequestMapping("/show")
    public String show(@SessionAttribute(value = "user",required = false)User user, HttpSession session){
        //判断用户是否登陆
        Long uid = 0L;
        //如果登陆了替换为当前用户id
        if (user!=null){
            uid = user.getId();
        }
        //查询购物项
        try {
            List<ShopCart> shopCarts = cartService.showCart(uid);
            //设置到作用域并重定向到购物车页面
            session.setAttribute("shopCar",shopCarts);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/shopping.jsp";
    }
}
