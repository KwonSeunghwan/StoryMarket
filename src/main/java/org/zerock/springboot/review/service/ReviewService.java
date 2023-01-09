package org.zerock.springboot.review.service;

import java.util.List;

import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.review.dto.ReviewDTO;
import org.zerock.springboot.review.dto.ReviewRegForm;
import org.zerock.springboot.review.entity.Review;

public interface ReviewService {
	//상품의 모든 리뷰를 가져온다.
    List<ReviewDTO> getListOfItem(Long ino);
    //상품 리뷰를 추가
    Long register(ReviewDTO itemReviewDTO);
    //특정한 상품리뷰 수정
    void modify(ReviewDTO itemReviewDTO);
    //상품 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO itemReviewDTO){
        Review itemReview = Review.builder()
                .reviewnum(itemReviewDTO.getReviewnum())
                .item(Item.builder().id(itemReviewDTO.getIno()).build())
                .member(Member.builder().id(itemReviewDTO.getLoginId()).build())
                .grade(itemReviewDTO.getGrade())
                .text(itemReviewDTO.getText())
                .imgPath(itemReviewDTO.getImgPath())
                .build();
        return itemReview;
    }

    default ReviewDTO entityToDto(Review itemReview){
        ReviewDTO itemReviewDTO = ReviewDTO.builder()
                .reviewnum(itemReview.getReviewnum())
                .ino(itemReview.getItem().getId())
                .name(itemReview.getMember().getName())
                .loginId(itemReview.getMember().getId())
                .grade(itemReview.getGrade())
                .text(itemReview.getText())
                .imgPath(itemReview.getImgPath())
                .regDate(itemReview.getRegDate())
                .modDate(itemReview.getModDate())
                .build();
        return itemReviewDTO;
    }
    
	void register(ReviewRegForm regForm);
}