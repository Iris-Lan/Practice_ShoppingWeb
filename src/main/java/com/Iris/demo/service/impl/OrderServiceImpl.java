package com.Iris.demo.service.impl;

import com.Iris.demo.entity.Members;
import com.Iris.demo.entity.Order;
import com.Iris.demo.entity.Products;
import com.Iris.demo.model.SearchOrderReqModel;
import com.Iris.demo.repository.MemberRepository;
import com.Iris.demo.repository.OrderRepository;
import com.Iris.demo.service.OrderService;
import com.Iris.demo.utils.JsonUtil;
import com.Iris.demo.utils.PageRequestUtil;
import com.Iris.demo.utils.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;


    /**
     * Ans3: Complete an order to buy products.
     *
     * @param products product lists
     * @param memberUUID member id
     * @return result
     */
    @Override
    @Transactional
    public String saveOrder(List<Products> products, String memberUUID) {
        Members member;
        try{
            Optional<Members> optMember = memberRepository.findById(memberUUID);
            //if member does exist
            if (optMember.isPresent()){
                member = optMember.get();
                Order order = Order.builder()
                        .orderId(UUIDGenerator.createUUID())
                        .orderDate(LocalDateTime.now())
                        .member(member)
                        .products(products)
                        .totalPrice(products.stream().mapToInt(Products::getPrice).sum())
                        .build();

                // The total amount of orders including this order.
                member.setOrderNumberTotal(member.getOrders().size() + 1);
                //OneToMany save
                member.getOrders().add(order);
                memberRepository.save(member);
//                orderRepository.save(order);
                return "Insert OK";
            }else{
                return "Member doesn't exist.";
            }
        }catch (RuntimeException e){
            log.error("Runtime error:{}", e.getMessage());
            return "Runtime error:" +  e.getMessage();
        }catch (Exception e){
            log.error("Exception error:{}", e.getMessage());
            return "Exception error: " +  e.getMessage();
        }
    }

    /**
     * Ans4: Search orders by orderId/productName/orderDate with Page setting.
     *
     * @param searchOrderReqModel orderId/productName/orderDate
     * @return orders
     */
    @Override
    @Transactional
    public List<Order> getOrders(SearchOrderReqModel searchOrderReqModel) {
        try {
            // Find by orderId
            if (!searchOrderReqModel.getOrderId().isEmpty()) {
                Pageable pageRequest = PageRequestUtil.of(
                        searchOrderReqModel.getDisplayPageNumber(), searchOrderReqModel.getNumberOfRowsPerPage(),
                        searchOrderReqModel.getOrdering(), "orderId");
                Order order = orderRepository.findByOrderId(searchOrderReqModel.getOrderId());
                Page<Order> page = orderRepository.findByOrderId(searchOrderReqModel.getOrderId(), pageRequest);
//                return new ArrayList<>(page.getContent());
                return page.getContent();

                // Find by productName
            } else if (!searchOrderReqModel.getProductName().isEmpty()) {
                // Find the orders including the target product.
                List<Order> orders = orderRepository.findAllByOrderByOrderDateDesc()
                        .stream()
                        .filter(
                            order -> order.getProducts().stream().anyMatch(
                                    product -> product.getProductName().contains(searchOrderReqModel.getProductName())
                        )).collect(Collectors.toList());

                PageRequest pageRequest = PageRequestUtil.of(
                        searchOrderReqModel.getDisplayPageNumber(), searchOrderReqModel.getNumberOfRowsPerPage(),
                        searchOrderReqModel.getOrdering(), "orderDate");

                //Convert list to page
                Page<Order> page = listConvertToPage(orders, pageRequest);
                return new ArrayList<>(page.getContent());

                // Find by orderDate
            } else {
                PageRequest pageRequest = PageRequestUtil.of(
                        searchOrderReqModel.getDisplayPageNumber(), searchOrderReqModel.getNumberOfRowsPerPage(),
                        searchOrderReqModel.getOrdering(), "orderDate");
                Page<Order> page = orderRepository.findByOrderDate(searchOrderReqModel.getOrderDate(), pageRequest);
                return new ArrayList<>(page.getContent());
            }

        }catch (RuntimeException e){
            log.error("Runtime error:{}", e.getMessage());
            return null;
        }catch (Exception e){
            log.error("Exception error:{}", e.getMessage());
            return null;
        }
    }


    /**
     * Ans5: Find member who has more than 'orderNum' orders.
     *
     * @param orderNum order amount condition
     * @return members who matched the condition
     */
    @Override
    @Transactional
    public List<Members> getMembersForAns5(Integer orderNum) {
        try {
            return memberRepository.findAllByOrderByOrderNumberTotalDesc()
                    .stream()
                    .filter(member -> member.getOrderNumberTotal() > orderNum)
                    .collect(Collectors.toList());
        }catch (RuntimeException e){
            log.error("Runtime error:{}", e.getMessage());
            return null;
        }catch (Exception e){
            log.error("Exception error:{}", e.getMessage());
            return null;
        }
    }


    /**
     * Convert list to page.
     *
     * @param orders order list
     * @param pageRequest page setting
     * @return page result
     */
    private Page<Order> listConvertToPage(List<Order> orders, PageRequest pageRequest) {
        int start = (int) pageRequest.getOffset();
        int end = (start + pageRequest.getPageNumber()) > orders.size() ? orders.size() : (start + pageRequest.getPageSize());
        return new PageImpl<>(orders.subList(start, end), pageRequest, orders.size());
    }


}
