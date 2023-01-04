package org.zerock.springboot.item.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.zerock.springboot.constant.ItemSellStatus;
import org.zerock.springboot.item.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotNull(message = "재고(입고 수량)는 필수 입력 값입니다.")
    private Integer stockNumber;
    
    @NotBlank(message = "배송 종류는 필수 입력 값입니다.")
    private String delivery;
    
    @NotBlank(message = "포장 타입은 필수 입력 값입니다.")
    private String packaging;
    
    @NotBlank(message = "판매 단위는 필수 입력 값입니다.")
    private String unit;
    
    @NotBlank(message = "중량/용량은 필수 입력 값입니다.")
    private String weight;
    
    @NotBlank(message = "상품 분류는 필수 입력 값입니다.")
    private String category;
    
    @NotBlank(message = "판매자는 필수 입력 값입니다.")
    private String register;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
}