package org.zerock.springboot.review.dto;

import org.springframework.web.multipart.MultipartFile;
import org.zerock.springboot.review.entity.Review;

import lombok.Data;

@Data
public class ReviewRegForm {
	private Long ino;
	private String loginId;
	private String text;
	private MultipartFile imgFile;
	
	public Review toReview() {
		Review review = Review.builder()
				.
		return null;
	}
}