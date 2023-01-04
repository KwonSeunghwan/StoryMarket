package org.zerock.springboot.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass		// 테이블로 생성되지 않음
@EntityListeners(value= {AuditingEntityListener.class})	// Entity 객체가 생성/변경되는 것을 감지
@Getter
public abstract class BaseEntity {
	// 생성시간
	@CreatedDate
	@Column(name="regdate",updatable=false)	// 테이블 컬럼이름
	private LocalDateTime regDate;			// 클래스의 필드 이름
	
	// 변경시간
	@LastModifiedDate
	@Column(name="moddate")	// 생성시간, 변경시간
	private LocalDateTime modDate;
}