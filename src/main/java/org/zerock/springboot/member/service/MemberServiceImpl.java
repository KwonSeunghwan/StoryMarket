package org.zerock.springboot.member.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.springboot.member.dto.MemberDTO;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.repository.MemberRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder pwencoder;
	
	@Override
	public void joinUser(MemberDTO memberDTO) {
		log.info(memberDTO);
		String encPw = pwencoder.encode(memberDTO.getPassword());
		memberDTO.setPassword(encPw);
		Member member = dtoToEntity(memberDTO);
		memberRepository.save(member);
	}
}
