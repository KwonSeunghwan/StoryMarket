package org.zerock.springboot.order.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.zerock.springboot.constant.OrderStatus;
import org.zerock.springboot.order.entity.Order;
import org.zerock.springboot.order.entity.OrderState;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderHistDto {

    public OrderHistDto(Order order, OrderState os){
        this.orderId = order.getId();
        this.orderDate = order.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = os.getOrderStatus();
    }

    private Long orderId; //주문아이디

    private String orderDate; //주문날짜

    private OrderStatus orderStatus; //주문 상태

    //주문 상품 리스트
    private List<OrderItemDto2> orderItemDtoList = new ArrayList<>();

    public void addOrderItemDto(OrderItemDto2 orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }

}
