package org.zerock.springboot.cart.service;

import org.zerock.springboot.cart.dto.CartDetailDto;
import org.zerock.springboot.cart.dto.CartItemDto;
import org.zerock.springboot.cart.entity.Cart;
import org.zerock.springboot.cart.entity.CartItem;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.item.entity.ItemImg;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.cart.repository.CartItemRepository;
import org.zerock.springboot.cart.repository.CartRepository;
import org.zerock.springboot.item.repository.ItemImgRepository;
import org.zerock.springboot.item.repository.ItemRepository;
import org.zerock.springboot.member.repository.MemberRepository;
import org.zerock.springboot.order.dto.OrderRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository imageRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String mid){
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityExistsException::new);
        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.get();

        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartItem savedCartItem =
                cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if(savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        }else {
            CartItem cartItem =
                    CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String mid){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.get();
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            return cartDetailDtoList;
        }

        List<Object[]> itemList = cartItemRepository.findCartDetailDtoList(cart.getId());
        
        for(Object[] objList : itemList) {
        	CartItem ci = (CartItem)objList[0];
        	Item item = (Item)objList[1];
        	CartDetailDto dto = entityToDto(ci, item);
        	cartDetailDtoList.add(dto);
        }

        return cartDetailDtoList;
    }

    private CartDetailDto entityToDto(CartItem ci, Item item) {
    	ItemImg ii = imageRepository.findByItemIdAndRepimgYn(item.getId(), "Y");
		return new CartDetailDto(
				ci.getId(), item.getId(), item.getItemNm(), item.getPrice(), ci.getCount(), ii.getImgUrl()
			);
	}

	@Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String mid){
        Optional<Member> result = memberRepository.findById(mid);
        Member curMember = result.get();
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMember.getId(), savedMember.getId())){
            return false;
        }

        return true;
    }

    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    @Transactional
	public void deleteCartItems(List<OrderRequest> orderDtoList) {
		for(OrderRequest orderRequest : orderDtoList) {
			deleteCartItem(orderRequest.getCartItemId());
		}
	}

    /*
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String mid){
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, mid);

        for (CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository
                    .findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityExistsException::new);
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }
    */
}
