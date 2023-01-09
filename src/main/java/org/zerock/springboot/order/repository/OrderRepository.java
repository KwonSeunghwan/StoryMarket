package org.zerock.springboot.order.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.springboot.order.dto.OrderDTO;
import org.zerock.springboot.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("select o, os, oi, i, m from Order o " +
			"left outer join OrderState os on os.order = o " +
			"left outer join o.member m " + 
			"left outer join OrderItem oi on oi.order = o " +
			"left outer join Item i on i.id = oi.item.id " +
			"where o.id = :orderId and o.member.id = :loginId")
	Object[] getOrderInfo(@Param("loginId") String loginId, @Param("orderId") Long orderId);

//    @Query("select o from Order o "+
//    "where o.member.id = :id " +
//    "order by o.orderDate desc")
//    List<Order> findOrders(@Param("id")String id, Pageable pageable);

//    @Query("select count(o) from Order o " +
//            "where o.member.id = :id")
//    Long countOrder(@Param("id") String id);
}