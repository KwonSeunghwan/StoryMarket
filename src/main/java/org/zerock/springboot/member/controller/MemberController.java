package org.zerock.springboot.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.springboot.member.dto.MemberDTO;
import org.zerock.springboot.member.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@AllArgsConstructor
public class MemberController {
	private MemberService memberService;

	// 회원가입 페이지
	@GetMapping("/user/signup")
	public String dispSignup() {
		return "regist";
	}

	// 회원가입 처리
	@PostMapping("/user/signup")
	public String execSignup(MemberDTO memberDTO) {
		log.info("signup..." + memberDTO);
		
		memberService.joinUser(memberDTO);

		return "redirect:/user/login";
	}

	// 로그인 페이지
	@GetMapping("/user/login")
	public String dispLogin() {
		return "/login";
	}

	// 로그인 결과 페이지
	@GetMapping("/user/login/result")
	public String dispLoginResult() {
		return "/loginSuccess";
	}

	// 로그아웃 결과 페이지
	@GetMapping("/user/logout/result")
	public String dispLogout() {
		return "/logout";
	}

	// 접근 거부 페이지
	@GetMapping("/user/denied")
	public String dispDenied() {
		return "/denied";
	}

	// 내 정보 페이지
	@GetMapping("/user/mypage")
	public String dispMyInfo() {
		return "/mypage";
	}

	// 어드민 페이지
	@GetMapping("/admin")
	public String dispAdmin() {
		return "/admin";
	}
}