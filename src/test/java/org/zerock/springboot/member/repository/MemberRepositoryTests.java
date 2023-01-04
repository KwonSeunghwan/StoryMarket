package org.zerock.springboot.member.repository;

import java.util.HashSet;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.member.entity.MemberRole;

@SpringBootTest
public class MemberRepositoryTests {
	@Autowired
	private MemberRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void insertDummies() {
		// 1 - 6까지는 USER만 지정
		// 7 - 8까지는 USER,MANAGER
		// 9 - 10까지는 USER,MANAGER,ADMIN

		IntStream.rangeClosed(1, 10).forEach(i -> {
			Member member = Member.builder()
					.loginId("user" + i)
					.email("user" + i + "@zerock.org")
					.password(passwordEncoder.encode("1111"))
					.name("사용자" + i)
					.phone("010-1234-5678")
					.roleSet(new HashSet<MemberRole>()).build();

			// default role
			member.addMemberRole(MemberRole.USER);
			if (i > 6) {
				member.addMemberRole(MemberRole.MANAGER);
			}
			if (i > 8) {
				member.addMemberRole(MemberRole.ADMIN);
			}
			repository.save(member);
		});
	}
}