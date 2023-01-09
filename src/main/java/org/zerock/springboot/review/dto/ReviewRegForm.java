package org.zerock.springboot.review.dto;

import org.springframework.web.multipart.MultipartFile;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.review.entity.Review;

import lombok.Data;

@Data
public class ReviewRegForm {
	private Long ino;
	private String loginId;
	private String text;
	private String imgPath;
	private MultipartFile imgFile;
	
	public Review toReview() {
		Item item = Item.builder().id(ino).build();
		Member member = Member.builder().id(loginId).build();
		Review review = Review.builder()
				.item(item)
				.member(member)
				.text(text)
				.imgPath(imgPath)
				.build();
		return review;
	}
}