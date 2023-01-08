package org.zerock.springboot.order.dto;

import java.time.LocalDateTime;

import org.zerock.springboot.item.dto.ItemDTO;

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
public class OrderItemDTO {
	private Long orderItemId;
	private ItemDTO item;
	private Long orderId;
	private int count;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}