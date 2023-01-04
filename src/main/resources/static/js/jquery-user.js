$(function(){



    setInterval(slide, 2000); /* index page img slide js*/
    var x = $(".imgslide img");
    var y = 4;
    function slide() {
        y--;
        if(y==0) {
            x.fadeIn();
            y=4;
        } else {
            x.eq(y).fadeOut();
        }
    }


});


$(document).ready(function() {

	// 기존 css에서 플로팅 배너 위치(top)값을 가져와 저장한다.
	var floatPosition = parseInt($("#quick_menu").css('top'));
	// 250px 이런식으로 가져오므로 여기서 숫자만 가져온다. parseInt( 값 );

	$(window).scroll(function() {
		// 현재 스크롤 위치를 가져온다.
		var scrollTop = $(window).scrollTop();
		var newPosition = scrollTop + floatPosition + "px";

		/* 애니메이션 없이 바로 따라감
		 $("#quick_menu").css('top', newPosition);
		 */

		$("#quick_menu").stop().animate({
			"top" : newPosition
		}, 400);

	}).scroll();



});

function goTop(){ 
	$('html').scrollTop(0);
	// scrollTop 메서드에 0 을 넣어서 실행
	// 이 소스가 동작하지 않는다면
	// $('html, body')로 변경
}
$(document).ready(function() {

	// 기존 css에서 플로팅 배너 위치(top)값을 가져와 저장한다.
	var floatPosition = parseInt($("#top_btn").css('top'));
	// 250px 이런식으로 가져오므로 여기서 숫자만 가져온다. parseInt( 값 );

	$(window).scroll(function() {
		// 현재 스크롤 위치를 가져온다.
		var scrollTop = $(window).scrollTop();
		var newPosition = scrollTop + floatPosition + "px";

		 $("#top_btn").css('top', newPosition);


        /* 애니메이션 효과를 받으며 따라감.
         $("#top_btn").stop().animate({
			"top" : newPosition
		}, 400); */

	}).scroll();


});