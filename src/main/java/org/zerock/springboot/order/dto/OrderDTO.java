package org.zerock.springboot.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.zerock.springboot.constant.OrderStatus;
import org.zerock.springboot.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
	private Long orderId;
	private int total;
	private OrderStatus orderStatus;
	private MemberDTO orderer;
	@Builder.Default
	private List<OrderItemDTO> orderItemList = new ArrayList<>();
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}