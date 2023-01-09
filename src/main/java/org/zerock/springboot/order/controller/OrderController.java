package org.zerock.springboot.order.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.springboot.cart.service.CartService;
import org.zerock.springboot.order.dto.OrderDTO;
import org.zerock.springboot.order.dto.OrderRegisterForm;
import org.zerock.springboot.order.dto.OrderRequest;
import org.zerock.springboot.order.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@Log4j2
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    
    @PostMapping("/register")
    public @ResponseBody ResponseEntity register(@RequestBody OrderRegisterForm orderForm, Principal principal) {
    	log.info("register..." + orderForm);

    	if(orderForm == null || orderForm.getOrderDtoList().size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }
    	
    	for (OrderRequest orderRequest : orderForm.getOrderDtoList()){
            if(!orderService.validateCartItem(orderRequest.getCartItemId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

    	Long orderId = orderService.register(orderForm.getOrderDtoList(), principal.getName());
    	
    	cartService.deleteCartItems(orderForm.getOrderDtoList());
    	return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping("/show")
    public String orderHist(Long orderId, Principal principal, Model model) {
        OrderDTO orderInfo = orderService.getOrderInfo(principal.getName(), orderId);

        model.addAttribute("orders", orderInfo);

        return "order/order";
    }
    
    @GetMapping("/info")
    public String orderInfo(Long orderId, Principal principal, Model model) {
    	OrderDTO orderInfo = orderService.getOrderInfo(principal.getName(), orderId);

        model.addAttribute("orders", orderInfo);

        return "order/orderInfo";
    }
    

    /*
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity<String> order(@RequestBody @Valid OrderDto orderDto,
                                              BindingResult bindingResult, Principal principal){

        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("" + orderId, HttpStatus.OK);
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder
            (@PathVariable("orderId")Long orderId, Principal principal){
        if (!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    */
}
