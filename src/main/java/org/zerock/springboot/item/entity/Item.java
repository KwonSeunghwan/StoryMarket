package org.zerock.springboot.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import org.zerock.springboot.common.entity.BaseEntity;
import org.zerock.springboot.constant.ItemSellStatus;
import org.zerock.springboot.exception.OutOfStockException;
import org.zerock.springboot.item.dto.ItemFormDto;
import org.zerock.springboot.member.entity.Member;

@Entity
@Builder
@Table(name = "item")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item extends BaseEntity{
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name = "price", nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량
    
    @Column(length = 20)
    private String delivery; // 배송 종류 : 일반/새벽
    
    @ManyToOne(fetch = FetchType.LAZY)	// 판매자
	private Member register;
    
    @Column(length = 30)
    private String packaging; // 포장 타입

    @Column(length = 50)
    private String weight; //중량

    @Column(length = 50)
    private String unit; //판매단위

    @Column(nullable = false)
    private String category; //상품분류

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber -stockNumber;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: "+ this.stockNumber +")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }
}