package org.zerock.springboot.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.springboot.common.dto.PageRequestDTO;
import org.zerock.springboot.common.dto.PageResultDTO;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.review.dto.ReviewDTO;
import org.zerock.springboot.review.dto.ReviewRegForm;
import org.zerock.springboot.review.entity.Review;
import org.zerock.springboot.review.repository.ReviewRepository;

import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	@Value("${org.zerock.upload.path}")
    private String uploadFolder;
	
	private final ReviewRepository reviewRepository;

	@Override
	public PageResultDTO<ReviewDTO, Review> getListOfItem(Long ino, PageRequestDTO pageRequestDTO) {
		Item item = Item.builder().id(ino).build();
        Page<Review> result = reviewRepository.findByItem(pageRequestDTO.getPageable(Sort.by("reviewnum").descending()), item);
        Function<Review, ReviewDTO> fn = (en -> entityToDto(en));
        return new PageResultDTO<>(result, fn);
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

	@Override
	public Long register(ReviewRegForm regForm) {
		MultipartFile imgFile = regForm.getImgFile();
		String uploadFileName = null;
		
		if(imgFile.getSize() > 0) {
			uploadFileName = imgFile.getOriginalFilename();
			
			// IE has file path -> 경로 자르기 (전체 경로 중에 파일 이름만 잘라낸다.)
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

			// 파일이름 중복 방지
			// 원래 파일 이름도 보존할 수 있다.
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			File saveFile = new File(uploadFolder, uploadFileName);
			try {
				imgFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		regForm.setImgPath(uploadFileName);
		Review review = regForm.toReview();
		reviewRepository.save(review);
		return review.getReviewnum();
	}
}