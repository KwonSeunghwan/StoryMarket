package org.zerock.springboot.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.review.dto.ReviewDTO;
import org.zerock.springboot.review.entity.Review;
import org.zerock.springboot.review.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;

	@Override
	public List<ReviewDTO> getListOfItem(Long ino) {
		Item item = Item.builder().id(ino).build();
        List<Review> result = reviewRepository.findByItem(item);
        return result.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());
	}

	@Override
	public Long register(ReviewDTO movieReviewDTO) {
		Review movieReview = dtoToEntity(movieReviewDTO);
        reviewRepository.save(movieReview);
        return movieReview.getReviewnum();
	}

	@Override
	public void modify(ReviewDTO movieReviewDTO) {
		Optional<Review> result =
                reviewRepository.findById(movieReviewDTO.getReviewnum());
        if(result.isPresent()){
            Review movieReview = result.get();
            movieReview.changeGrade(movieReviewDTO.getGrade());
            movieReview.changeText(movieReviewDTO.getText());
            reviewRepository.save(movieReview);
        }
	}

	@Override
	public void remove(Long reviewnum) {
		reviewRepository.deleteById(reviewnum);
	}
}