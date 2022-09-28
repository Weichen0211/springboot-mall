package com.weichen.springbootmall.service;

import com.weichen.springbootmall.dto.CreateOrderRequest;
import com.weichen.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);


}
