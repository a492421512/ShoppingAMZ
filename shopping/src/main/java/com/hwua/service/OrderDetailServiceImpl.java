package com.hwua.service;


import com.hwua.entity.OrderDetail;
import com.hwua.mapper.OrderDetailDao;
import com.hwua.service.I.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailDao orderDetailDao ;

    /**
     * 通过订单id查询订单明细
     * @param oid
     * @return
     * @throws SQLException
     */
    @Override
    public List<OrderDetail> queryOrderDetailByOid(Long oid) throws SQLException {
        return orderDetailDao.queryOrderDetailByOid(oid);
    }

    /**
     * 添加订单明细
     * @param orderDetail
     * @return
     * @throws SQLException
     */
    @Override
    public Integer insertOrderDetail(OrderDetail orderDetail) throws SQLException {
        return orderDetailDao.insertOrderDetail(orderDetail);
    }
}
