package org.zerock.springboot.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.springboot.cart.dto.CartDetailDto;
import org.zerock.springboot.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select ci, i " +
            "from CartItem ci " +
            "left outer join ci.item i " +
            "where ci.cart.id = :cartId " +
            "order by ci.regDate desc ")
    List<Object[]> findCartDetailDtoList(@Param("cartId") Long cartId);
}
