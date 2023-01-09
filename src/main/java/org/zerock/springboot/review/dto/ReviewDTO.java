package org.zerock.springboot.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
	//review num
    private Long reviewnum;
    // Item mno
    private Long ino;
    //Member name
    private String name;
    //Member loginId
    private String loginId;
    private int grade;
    private String text;
    private String imgPath;
    private LocalDateTime regDate, modDate;
}
