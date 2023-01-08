package org.zerock.springboot.item.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class ItemDTO {
	private Long itemId;
	private String registerId;
	private String category;
	private String delivery;
	private String itemNm;
	private int price;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	private List<ItemImgDto> imgList = new ArrayList<>();
}