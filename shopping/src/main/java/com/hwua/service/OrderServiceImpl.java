package com.hwua.service;


import com.hwua.entity.Order;
import com.hwua.entity.OrderDetail;
import com.hwua.mapper.OrderDao;
import com.hwua.service.I.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao ;

    /**
     * 查询订单
     * @param uid
     * @return
     * @throws SQLException
     */
    @Override
    public List<Order> queryOrderByUid(Long uid) throws SQLException {
        return orderDao.queryOrderByUid(uid);
    }


    @Override
    public Boolean insertOrder(Order order) throws SQLException{
        //插入订单。
        Integer integer = orderDao.insertOrder(order);
        if (integer>0){
            return true;
        }
        return false;
    }
}
