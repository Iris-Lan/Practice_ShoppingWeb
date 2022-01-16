package com.Iris.demo.controller;

import com.Iris.demo.entity.Members;
import com.Iris.demo.entity.Order;
import com.Iris.demo.entity.Products;
import com.Iris.demo.model.SaveOrderReqModel;
import com.Iris.demo.model.SearchOrderReqModel;
import com.Iris.demo.service.MemberService;
import com.Iris.demo.service.OrderService;
import com.Iris.demo.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Ans3-5
 *
 * @author Iris Lan
 * @since 2022.1.16
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
    private final OrderService orderService;

    /**
     * Complete an order to buy products.
     */
    @PostMapping(value = "/saveOrder")
    public String saveOrder(@Valid @RequestBody SaveOrderReqModel saveOrderReqModel){
        log.info("saveOrder products data:{}, member id:{}", JsonUtil.getJsonString(saveOrderReqModel.getProducts()), JsonUtil.getJsonString(saveOrderReqModel.getMemberUUID()));
        return orderService.saveOrder(saveOrderReqModel.getProducts(), saveOrderReqModel.getMemberUUID());
    }

    /**
     * Search orders by orderId/productName/orderDate with Page setting.
     */
    @GetMapping(value = "/getOrders")
    public List<Order> getOrders(@Valid @RequestBody SearchOrderReqModel searchOrderReqModel){
        log.info("getOrders searchOrderReqModel:{}", JsonUtil.getJsonString(searchOrderReqModel));
        return orderService.getOrders(searchOrderReqModel);
    }

    /**
     * Find member who has more than 'orderNum' orders.
     */
    @GetMapping(value = "/getMembersForAns5")
    public List<Members> getMembersForAns5(@NotNull @RequestParam Integer orderNum){
        log.info("getMembersForAns5 orderNum:{}", orderNum);
        return orderService.getMembersForAns5(orderNum);
    }

}
