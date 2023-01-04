package org.zerock.springboot.member.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthMemberDTO extends User {
	private String name;
	private MemberDTO member; // 구글 프로파일에 있는 정보

	public AuthMemberDTO(MemberDTO member, Collection<? extends GrantedAuthority> authorities,
			Map<String, Object> attr) {
		this(member, authorities);
	}

	public AuthMemberDTO(MemberDTO member, Collection<? extends GrantedAuthority> authorities) {
		super(member.getLoginId(), member.getPassword(), authorities);
		this.member = member;
		this.name = member.getName();
	}
}