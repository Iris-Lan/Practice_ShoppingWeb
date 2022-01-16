package com.Iris.demo.repository;

import com.Iris.demo.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findByOrderId(String orderId, Pageable pageRequest);

    Page<Order> findByOrderDate(LocalDateTime orderDate, PageRequest pageRequest);

    List<Order> findAllByOrderByOrderDateDesc();

    Order findByOrderId(String orderId);
}
