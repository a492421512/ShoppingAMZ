package com.hwua.service;

import com.hwua.entity.ShopCart;
import com.hwua.mapper.ShopCartDao;
import com.hwua.service.I.ShopCartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ShopCartServiceImplTest {
    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private ShopCartDao shopCart;
    @Test
    public void showCart() throws SQLException {
        System.out.println(shopCart);
        System.out.println(shopCartService);
        List<ShopCart> shopCarts = shopCart.showCart(0L);
        System.out.println(shopCarts);
    }
}