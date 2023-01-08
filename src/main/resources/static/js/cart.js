$(document).ready(function() {
	var csrfHeaderName = window.localStorage.getItem("csrfHeaderName");
	var csrfTokenValue = window.localStorage.getItem("csrfTokenValue");

	$("input[name=cartChkBox]").change(function(e) {
		getOrderTotalPrice();
		var $liTag = $(this).parent().parent();
		var thisTag = $(this);
		if(thisTag[0].checked == true) {
			$liTag.find(".amount_down").attr("disabled", false);
			$liTag.find(".amount_up").attr("disabled", false);
			$liTag.find(".product_count_tmp").attr("disabled", false);
		} else {
			$liTag.find(".amount_down").attr("disabled", true);
			$liTag.find(".amount_up").attr("disabled", true);
			$liTag.find(".product_count_tmp").attr("disabled", true);
		}
	});

	// 선택 삭제
	$(".choice-check").on("click", function(e) {
		if (confirm("정말로 삭제하시겠습니까?")) {
			$("input[name='cartChkBox']:checked").each(function(e) {
				// 선택된 장바구니 삭제
				console.log("cart number: " + $(this).val());
				deleteCartItem($(this).val());
			});
		}
	});

	// 수량 감소 버튼 클릭
	$(".amount_down").on("click", function(e) {
		// cartItem 찾기
		var $liTag = $(this).parent().parent();
		var $countTag = $liTag.find("input[name='count']");
		if($countTag.val() > 0) {
			$countTag.val($countTag.val() - 1);
			const basketPrice = $liTag.data("price") * $countTag.val();
			$liTag.find(".product-basket-price span").text(basketPrice + '원');
			getOrderTotalPrice();
		}
		console.log("count = " + $countTag.val());
	});
	
	// 수량 증가 버튼 클릭
	$(".amount_up").on("click", function(e) {
		// cartItem 찾기
		var $liTag = $(this).parent().parent();
		var $countTag = $liTag.find("input[name='count']");
		if($countTag.val() >= 0 && $countTag.val() < 10) {
			$countTag.val(Number($countTag.val()) + 1);
			const basketPrice = $liTag.data("price") * $countTag.val();
			$liTag.find(".product-basket-price span").text(basketPrice + '원');
			getOrderTotalPrice();
		}
		console.log("count = " + $countTag.val());
	});
	
	$("input[name='count'").on("change", function(e) {
		// cartItem 찾기
		var $liTag = $(this).parent().parent();
		const basketPrice = $liTag.data("price") * $(this).val();
		$liTag.find(".product-basket-price span").text(basketPrice + '원');
		getOrderTotalPrice();
		console.log("count = " + $countTag.val());
	});
	
	$(".basket-order-btn").on("click", function(e) {
		e.preventDefault();
		orders();
	});
	
	$("#checkall").on("click", function(e) {
		checkAll();
	});

	function deleteCartItem(cartItemId) {
		var url = "/cartItem/" + cartItemId;

		$.ajax({
			url: url,
			type: "DELETE",
			beforeSend: function(xhr) {
				/* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			dataType: "json",
			cache: false,
			success: function(result, status) {
				location.href = '/cart';
			},
			error: function(jqXHR, status, error) {

				if (jqXHR.status == '401') {
					alert('로그인 후 이용해주세요');
					location.href = '/members/login';
				} else {
					alert(jqXHR.responseText);
				}

			}
		});
	}
	
	function getOrderTotalPrice() {
		var orderSumPrice = 0;
		var shippingFee = 0;
		$("input[name=cartChkBox]:checked").each(function() {
			var $liTag = $(this).parent().parent();
			var cartItemId = $liTag.data("id");
			var price = $liTag.data("price");
//			var count = Number($liTag.children(".product-amount").children("input[name='count']").val());
			var count = Number($liTag.find("input[name='count']").val());
			orderSumPrice += price * count;
		});
	
		$(".basket-total-price .sumation").text(orderSumPrice.toLocaleString('ko-KR'));
		if(orderSumPrice > 0) {
			shippingFee = 3000;
		}
		$(".basket-total-price .shipping").text(shippingFee.toLocaleString('ko-KR'));
		var orderTotalPrice = orderSumPrice + shippingFee;
		$(".basket-total-price .total").text(orderTotalPrice.toLocaleString('ko-KR'));
	}
	
	function orders() {
		var url = "/order/register";
	
		var dataList = new Array();
		var paramData = new Object();
	
		$("input[name=cartChkBox]:checked").each(function() {
			var $liTag = $(this).parent().parent();
			var cartItemId = $liTag.data("id");
			var data = new Object();
			data["cartItemId"] = cartItemId;
			data["count"] = $liTag.find("input[name='count']").val();
			dataList.push(data);
		});
	
		paramData['orderDtoList'] = dataList;
	
		var param = JSON.stringify(paramData);
	
		$.ajax({
			url: url,
			type: "POST",
			contentType: "application/json",
			data: param,
			beforeSend: function(xhr) {
				/* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			dataType: "json",
			cache: false,
			success: function(result, status) {
				console.log("orderId = " + result);
				alert("주문이 완료 되었습니다.");
				location.href = '/order/show?orderId=' + result;
			},
			error: function(jqXHR, status, error) {
	
				if (jqXHR.status == '401') {
					alert('로그인 후 이용해주세요');
					location.href = '/members/login';
				} else {
					alert(jqXHR.responseJSON.message);
				}
	
			}
		});
	}
	
	function checkAll() {
		if ($("#checkall").prop("checked")) {
			$("input[name=cartChkBox]").prop("checked", true);
		} else {
			$("input[name=cartChkBox]").prop("checked", false);
		}
		getOrderTotalPrice();
	}
	
	getOrderTotalPrice();
});

