package org.zerock.springboot.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.springboot.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
