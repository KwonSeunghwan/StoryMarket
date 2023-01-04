package org.zerock.springboot.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.springboot.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
//	 Optional<Member> findByLoginId(String loginId);
}
