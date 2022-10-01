package com.weichen.springbootmall.service;

import com.weichen.springbootmall.dto.CreateOrderRequest;
import com.weichen.springbootmall.dto.OrderQueryParams;
import com.weichen.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);


}
