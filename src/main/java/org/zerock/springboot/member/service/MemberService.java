package org.zerock.springboot.member.service;


import java.util.HashSet;

import org.zerock.springboot.member.dto.MemberDTO;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.entity.MemberRole;

public interface MemberService {

	void joinUser(MemberDTO memberDTO);
	
	// DTO -> Entity
    default Member dtoToEntity(MemberDTO memberDTO){
        Member member = Member.builder()
        		.loginId(memberDTO.getLoginId())
        		.password(memberDTO.getPassword())
        		.name(memberDTO.getName())
        		.email(memberDTO.getEmail())
        		.phone(memberDTO.getPhone())
        		.postCode(memberDTO.getPostCode())
        		.addr(memberDTO.getAddr())
        		.detAddr(memberDTO.getDetAddr())
        		.gender(memberDTO.getGender())
        		.birthday(memberDTO.getBirthday())
        		.roleSet(new HashSet<MemberRole>())
        		.build();
        member.addMemberRole(MemberRole.USER);
        return member;
    }
    
    // Entity(Member) -> MemberDTO
    default MemberDTO entityToDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
        		.loginId(member.getLoginId())
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
