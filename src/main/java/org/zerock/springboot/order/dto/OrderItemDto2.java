package org.zerock.springboot.order.dto;

import org.zerock.springboot.order.entity.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto2 {

    public OrderItemDto2(OrderItem orderItem, String imgUrl){
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getItem().getPrice();
        this.imgUrl = imgUrl;
    }

    private String itemNm; //상품명

    private int count; //주문 수량

    private int orderPrice; //주문 금액

    private String imgUrl; //상품 이미지 경로
}
