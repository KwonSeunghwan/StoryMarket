package org.zerock.springboot.order.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRegisterForm {
	List<OrderRequest> orderDtoList;
}