package org.zerock.springboot.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDTO {

    @NotNull(message = "장바구니 아이디는 필수 입력 값 입니다.")
    private Long cartId;

    @NotBlank(message = "장바구니 주인 아이디는 필수 입력값 입니다.")
    private String loginId;
    
    @Builder.Default
    private List<CartDetailDto> itemList = new ArrayList<>();
    
    private LocalDateTime regDate;
	private LocalDateTime modDate;
}
