package org.zerock.springboot.order.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import org.zerock.springboot.cart.entity.CartItem;
import org.zerock.springboot.cart.repository.CartItemRepository;
import org.zerock.springboot.constant.OrderStatus;
import org.zerock.springboot.item.dto.ItemDTO;
import org.zerock.springboot.item.dto.ItemImgDto;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.item.entity.ItemImg;
import org.zerock.springboot.item.repository.ItemImgRepository;
import org.zerock.springboot.member.dto.MemberDTO;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.repository.MemberRepository;
import org.zerock.springboot.order.dto.OrderDTO;
import org.zerock.springboot.order.dto.OrderItemDTO;
import org.zerock.springboot.order.dto.OrderRequest;
import org.zerock.springboot.order.entity.Order;
import org.zerock.springboot.order.entity.OrderItem;
import org.zerock.springboot.order.entity.OrderState;
import org.zerock.springboot.order.repository.OrderItemRepository;
import org.zerock.springboot.order.repository.OrderRepository;
import org.zerock.springboot.order.repository.OrderStateRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStateRepository orderStateRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemImgRepository imageRepository;

    /*
    public Long order(OrderDto orderDto, String mid){
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.get();

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String mid, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(mid, pageable);
        Long totalCount = orderRepository.countOrder(mid);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String mid){
        Optional<Member> result = memberRepository.findById(mid);
        Member curMember = result.get();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getId(), savedMember.getId())){
            return false;
        }

        return true;
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String mid){
        Optional<Member> result = memberRepository.findById(mid);
        Member member = result.get();
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderDto orderDto : orderDtoList){
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
    */
    
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

    @Transactional
	public Long register(List<OrderRequest> orderList, String loginId) {
		Member user = Member.builder().id(loginId).build();
		Order order = Order.builder()
				.member(user)
				.build();
		orderRepository.save(order);
		
		for (OrderRequest orderRequest : orderList) {
			Object[] result = cartItemRepository.findByCartItemId(orderRequest.getCartItemId());
			Object[] datas = (Object[])result[0];
			Item item = (Item)datas[1];
			OrderItem oi = OrderItem.builder()
					.item(item)
					.order(order)
					.count(orderRequest.getCount())
					.build();
			orderItemRepository.save(oi);
		}
		
		OrderState os = OrderState.builder()
				.order(order)
				.orderStatus(OrderStatus.ORDER)
				.build();
		orderStateRepository.save(os);
		return order.getId();
	}

	public OrderDTO getOrderInfo(String loginId, Long orderId) {
		Object[] result = orderRepository.getOrderInfo(loginId, orderId);
		Object[] orderitems = (Object[])result[0];
		OrderDTO dto = entityToDto((Order)orderitems[0], (OrderState)orderitems[1], (Member)orderitems[4]);
		List<OrderItemDTO> list = dto.getOrderItemList();
		int total = 0;
		for(Object obj : result) {
			Object[] ois = (Object[])obj;
			OrderItemDTO oiDTO = entityToDTOConversion((Order)ois[0], (OrderItem)ois[2], (Item)ois[3]);
			total += (oiDTO.getCount() * oiDTO.getItem().getPrice());
			list.add(oiDTO);
		}
		dto.setOrderItemList(list);
		dto.setTotal(total);
		return dto;
	}

	private OrderItemDTO entityToDTOConversion(Order order, OrderItem oi, Item item) {
		List<ItemImg> imgList = imageRepository.findByItemIdOrderByIdAsc(oi.getId());
		List<ItemImgDto> imgDTOList = imgList.stream().map(en -> ItemImgDto.of(en)).collect(Collectors.toList());
		ItemDTO dto = itemEntityToItemDTO(item, imgDTOList);
		OrderItemDTO oiDTO = OrderItemDTO.builder()
				.orderItemId(oi.getId())
				.item(dto)
				.orderId(order.getId())
				.count(oi.getCount())
				.regDate(oi.getRegDate())
				.modDate(oi.getModDate())
				.build();
		return oiDTO;
	}

	private ItemDTO itemEntityToItemDTO(Item item, List<ItemImgDto> imgDTOList) {
		ItemDTO itemDTO = ItemDTO.builder()
				.itemId(item.getId())
				.registerId(item.getRegister().getId())
				.category(item.getCategory())
				.delivery(item.getDelivery())
				.itemNm(item.getItemNm())
				.price(item.getPrice())
				.regDate(item.getRegDate())
				.modDate(item.getModDate())
				.imgList(imgDTOList)
				.build();
		return itemDTO;
	}

	private OrderDTO entityToDto(Order order, OrderState os, Member m) {
		MemberDTO orderer = memberEntityToMemberDTO(m);
		OrderDTO dto = OrderDTO.builder()
				.orderId(order.getId())
				.orderStatus(os.getOrderStatus())
				.orderer(orderer)
				.regDate(order.getRegDate())
				.modDate(order.getModDate())
				.build();
		return dto;
	}

	private MemberDTO memberEntityToMemberDTO(Member m) {
		MemberDTO dto = MemberDTO.builder()
				.loginId(m.getId())
				.password(m.getPassword())
				.name(m.getName())
				.email(m.getEmail())
				.phone(m.getPhone())
				.postCode(m.getPostCode())
				.addr(m.getAddr())
				.detAddr(m.getDetAddr())
				.gender(m.getGender())
				.birthday(m.getBirthday())
				.role(m.getRole())
				.regDate(m.getRegDate())
				.modDate(m.getModDate())
				.build();
		return dto;
	}
}