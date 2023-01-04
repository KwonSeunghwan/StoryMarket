package org.zerock.springboot.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.springboot.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberId(String memberId);
}
