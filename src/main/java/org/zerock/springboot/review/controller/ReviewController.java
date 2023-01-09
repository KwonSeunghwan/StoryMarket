package org.zerock.springboot.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springboot.item.dto.ItemDTO;
import org.zerock.springboot.item.service.ItemService;
import org.zerock.springboot.review.dto.ReviewDTO;
import org.zerock.springboot.review.dto.ReviewRegForm;
import org.zerock.springboot.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/review")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	private final ItemService itemService;
	
//	@GetMapping("/{mno}/all")
//    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("ino") Long ino){
//        log.info("--------------list---------------");
//        log.info("INO: " + ino);
//        List<ReviewDTO> reviewDTOList = reviewService.getListOfItem(ino);
//        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
//    }
	
	@GetMapping("/register")
	public void registerGet(Long itemId, Model model) {
		log.info("register..." + itemId);
		ItemDTO item = itemService.getItem(itemId);
		model.addAttribute("item", item);
	}

    @PostMapping("/register")
    public String addReview(ReviewRegForm regForm, RedirectAttributes rttr){
    	log.info("addReview..." + regForm);
    	reviewService.register(regForm);
    }

//    @PutMapping("/{mno}/{reviewnum}")
//    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum,
//                                             @RequestBody ReviewDTO movieReviewDTO){
//        log.info("---------------modify MovieReview--------------" + reviewnum);
//        log.info("reviewDTO: " + movieReviewDTO);
//        reviewService.modify(movieReviewDTO);
//        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{mno}/{reviewnum}")
//    public ResponseEntity<Long> removeReview( @PathVariable Long reviewnum){
//        log.info("---------------modify removeReview--------------");
//        log.info("reviewnum: " + reviewnum);
//        reviewService.remove(reviewnum);
//        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
//    }
}