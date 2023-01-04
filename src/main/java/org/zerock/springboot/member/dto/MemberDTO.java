package org.zerock.springboot.member.dto;

import java.time.LocalDateTime;

import org.zerock.springboot.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

	private String loginId; // 로그인용 ID 값

	private String password; // 사용자 비밀번호
	private String name; // 사용자 이름

	private String email; // 사용자 이메일

	private String phone; // 사용자 전화번호
	private String postCode; // 우편번호
	private String addr; // 사용자 주소
	private String detAddr; // 사용자 상세 주소
	private String gender; // 사용자 성별
	private String birthday; // 사용자 생일
	private String role; // 사용자 권한
	private LocalDateTime regDate;
	private LocalDateTime modDate;

	public Member toEntity() {
		return Member.builder()
				.id(loginId)
				.password(password)
				.name(name)
				.email(email)
				.phone(phone)
				.postCode(postCode)
				.addr(addr)
				.detAddr(detAddr)
				.gender(gender)
				.birthday(birthday)
				.role(role)
				.build();
	}

	public MemberDTO(String loginId, String password, String name, String email, String phone, String postCode,
			String addr, String detAddr, String gender, String birthday, String role) {
		this.loginId=loginId;
		this.password=password;
		this.name=name;
		this.email=email;
		this.phone=phone;
		this.postCode=postCode;
		this.addr=addr;
		this.detAddr=detAddr;
		this.gender=gender;
		this.birthday=birthday;
		this.role=role;
	}
}
