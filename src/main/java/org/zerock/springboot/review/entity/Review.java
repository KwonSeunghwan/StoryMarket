package org.zerock.springboot.review.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.zerock.springboot.common.entity.BaseEntity;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.member.entity.Member;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(excludes = {"item", "member"})
public class Review extends BaseEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;
	@ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private int grade;
    private String text;
    private String imgPath;
    
    public void changeGrade(int grade) {
    	this.grade = grade;
    }
    
    public void changeText(String text) {
    	this.text = text;
    }
}