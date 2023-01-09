package org.zerock.springboot.review.repository;

import java.util.List;

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
	List<Review> findByItem(Item item);
	
	@Modifying
	@Query("delete from Review mr where mr.member = :member")
	void deleteByMember(@Param("member") Member member);
}