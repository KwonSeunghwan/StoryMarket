package org.zerock.springboot.common.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> {
	private int total;				// 전체 품목 수
	private int criTotal;			// 검색조건을 만족하는 품목 수
	private List<DTO> dtoList;		//DTO리스트
	private int totalPage;			//총 페이지 번호
    private int page;				//현재 페이지 번호
    private int size;				//목록 사이즈
    private int start, end;			//시작 페이지 번호, 끝 페이지 번호
    private boolean prev, next;		//이전, 다음
    private List<Integer> pageList;	//페이지 번호  목록

    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        criTotal = (int)result.getTotalElements();
        makePageList(result.getPageable());
    }
    
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;	// 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;	//temp end page
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd: totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}