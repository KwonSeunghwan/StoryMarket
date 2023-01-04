package org.zerock.springboot.member.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.springboot.member.dto.AuthMemberDTO;
import org.zerock.springboot.member.dto.MemberDTO;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// 인증이 성공하면 사용자 프로파일(principal)을 반환 

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	// 매개변수: userid(eamil)
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// ClubAuthMemberDTO 생성해서 반환한다.
		log.info("ClubUserDetailsService loadUserByUsername " + username);
		// from_social이 true인 회원정보를 실패(소셜가입자 정보로 로그인을 시도하면)
		Optional<Member> result = memberRepository.findById(username);
		if (result.isEmpty()) {
			throw new UsernameNotFoundException("Check User Email or from Social ");
		}

		Member member = result.get();
		log.info("-----------------------------");
//		log.info(member);

		// entity -> DTo 
		AuthMemberDTO clubAuthMember = new AuthMemberDTO(
				entityToDTO(member),
				member.getRoleSet().stream()
						.map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
						.collect(Collectors.toSet()));
		return clubAuthMember;	// securityContext로 저장(principal)
	}

	private MemberDTO entityToDTO(Member member) {
		MemberDTO memberDTO = MemberDTO.builder()
        		.loginId(member.getId())
        		.password(member.getPassword())
        		.name(member.getName())
        		.email(member.getEmail())
        		.phone(member.getPhone())
        		.postCode(member.getPostCode())
        		.addr(member.getAddr())
        		.detAddr(member.getDetAddr())
        		.gender(member.getGender())
        		.birthday(member.getBirthday())
        		.role(member.getRole())
        		.build();
        return memberDTO;
	}
}