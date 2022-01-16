package com.Iris.demo.service;

import com.Iris.demo.entity.Members;
import com.Iris.demo.entity.Order;
import com.Iris.demo.entity.Products;
import com.Iris.demo.model.SearchOrderReqModel;

import java.util.List;

public interface OrderService {
    String saveOrder(List<Products> products, String memberUUID);

    List<Order> getOrders(SearchOrderReqModel searchOrderReqModel);

    List<Members> getMembersForAns5(Integer orderNum);
}
