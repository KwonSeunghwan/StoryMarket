package org.zerock.springboot.member.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.zerock.springboot.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {

	@Id
	@Column(name = "login_id", length = 20, nullable = false)
	private String id; // 로그인용 ID 값

	@Column(length = 100, nullable = false)
	private String password; // 사용자 비밀번호

	private String name; // 사용자 이름

	@Column(length = 30, nullable = false, unique = true)
	private String email; // 사용자 이메일

	private String phone; // 사용자 전화번호
	private String postCode; // 우편번호
	private String addr; // 사용자 주소
	private String detAddr; // 사용자 상세 주소
	private String gender; // 사용자 성별
	private String birthday; // 사용자 생일
	private String role; // 사용자 권한

	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private Set<MemberRole> roleSet = new HashSet<>();

	public void addMemberRole(MemberRole clubMemberRole) {
		roleSet.add(clubMemberRole);
	}
}