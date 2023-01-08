package org.zerock.springboot.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
	private Long cartItemId;
	private int count;
}