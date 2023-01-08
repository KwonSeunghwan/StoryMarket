package org.zerock.springboot.order.dto;

import java.time.LocalDateTime;
import java.util.List;

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
	private MemberDTO orderer;
	private List<OrderItemDTO> orderItemList;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}