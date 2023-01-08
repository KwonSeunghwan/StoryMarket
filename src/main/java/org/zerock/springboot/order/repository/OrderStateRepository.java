package org.zerock.springboot.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.springboot.order.entity.OrderState;

public interface OrderStateRepository extends JpaRepository<OrderState, Long> {

}