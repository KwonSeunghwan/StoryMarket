package org.zerock.springboot.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.query.Param;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.member.entity.Member;
import org.zerock.springboot.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	@EntityGraph(attributePaths = {"member"}, type=EntityGraphType.FETCH)
	Page<Review> findByItem(Pageable pageable, Item item);
	
	@Modifying
	@Query("delete from Review mr where mr.member = :member")
	void deleteByMember(@Param("member") Member member);
}