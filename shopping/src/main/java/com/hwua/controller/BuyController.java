package com.hwua.controller;

import com.hwua.entity.*;
import com.hwua.service.I.OrderDetailService;
import com.hwua.service.I.OrderService;
import com.hwua.service.I.ProductService;
import com.hwua.service.I.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/doBuy")
public class BuyController {

    @Autowired
    private ProductService ps;
    @Autowired
    private ShopCartService shopCarSvs;
    @Autowired
    private OrderService orderSvs;
    @Autowired
    private OrderDetailService odSvs;

    //购物车结算
    @RequestMapping("/buy")
    public String buy(@SessionAttribute(value = "user",required = false)User user, HttpSession session){
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        //订单总价
        BigDecimal totalPrice = new BigDecimal(0);
        //获取当前时间字符串
        Timestamp times = new Timestamp(new Date().getTime());

        //没有登陆就直接登陆
        if (user==null){
            return "redirect:/login.jsp";
        }
            //获取用户id
            long uid = user.getId();
            try {

                //获取该用户的购物车
                List<ShopCart> shopCarts = shopCarSvs.showCart(uid);
                for (ShopCart shopCart : shopCarts) {
                    //累加购物项获得总价
                    totalPrice = totalPrice.add(shopCart.getSubtotal());
                }

                order.setUid(uid);//用户id
                order.setUname(user.getUname());//用户姓名
                order.setUaddress(user.getAddress());//用户地址
                order.setCreate_time(times);//时间
                order.setMoney(totalPrice);//金额
                //添加订单
                orderSvs.insertOrder(order);

                //获取本次订单的id
                List<Order> orders = orderSvs.queryOrderByUid(uid);
                long orderId = orders.get(0).getId();
                orderDetail.setOid(orderId);//设置订单id
                //每个购物项对应一个订单明细
                for (ShopCart shopCart : shopCarts) {
                    orderDetail.setPid(shopCart.getPid());//设置商品id
                    orderDetail.setQuantity(shopCart.getPnum());//设置商品数量
                    orderDetail.setMoney(shopCart.getSubtotal());//设置小计金额
                    //添加订单明细
                    odSvs.insertOrderDetail(orderDetail);
                    //删除对应的库存 = 原库存 - 购买的数量
                    Long pnum = shopCart.getPro().getStock() - shopCart.getPnum();
                    //调用方法更改库存
                    ps.delProStock(shopCart.getPro().getId(),pnum);
                }

                //清空购物车
                shopCarSvs.clearShopCart(uid);
                //把订单明细和订单放入到作用域,并跳转到订单页面
                session.setAttribute("orderList",orders);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        return "redirect:/shopping-result.jsp";
    }

    //购买单个商品
    @RequestMapping("/buyOne")
    public String buyOne(String pid,Long pnum,@SessionAttribute(value = "user",required = false)User user,HttpSession session){
        Order order = new Order();
        OrderDetail orderDetail = new OrderDetail();
        //订单总价
        BigDecimal totalPrice = new BigDecimal(0);
        //获取当前时间字符串
        Timestamp times = new Timestamp(new Date().getTime());

        //没有登陆就直接登陆
        if (user==null){
            return "redirect:/login.jsp";
        }

        //获取用户id
        long uid = user.getId();
        try {
            //查询商品单价
            Product product = ps.queryProductById(Integer.parseInt(pid));
            totalPrice = product.getPrice().multiply(new BigDecimal(pnum));//获取小计
            //添加订单
            order.setUid(uid);//用户id
            order.setUname(user.getUname());//用户姓名
            order.setUaddress(user.getAddress());//用户地址
            order.setCreate_time(times);//时间
            order.setMoney(totalPrice);//金额
            //添加订单
            orderSvs.insertOrder(order);
            //获取本次订单的id
            List<Order> orders = orderSvs.queryOrderByUid(uid);
            long orderId = orders.get(0).getId();
            orderDetail.setOid(orderId);//设置订单id
            orderDetail.setPid(Long.parseLong(pid));//设置商品id
            orderDetail.setQuantity(pnum);//设置商品数量
            orderDetail.setMoney(totalPrice);//设置小计金额
            //添加订单明细
            odSvs.insertOrderDetail(orderDetail);
            //删除对应库存
            Long num =product.getStock() - pnum;
            ps.delProStock(Long.parseLong(pid),num);
            //把订单明细和订单放入到作用域,并跳转到订单页面
            session.setAttribute("orderList",orders);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/shopping-result.jsp";
    }

    @RequestMapping("/show")
    public String show(@SessionAttribute(value = "user",required = false)User user,HttpSession session){
        //没有登陆就直接登陆
        if (user==null){
            return "redirect:/login.jsp";
        }
        //获取用户id
        long uid = user.getId();
        try {
            List<Order> orders = orderSvs.queryOrderByUid(uid);
            //把订单明细和订单放入到作用域,并跳转到订单页面
            session.setAttribute("orderList",orders);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/orders_view.jsp";
    }
}
