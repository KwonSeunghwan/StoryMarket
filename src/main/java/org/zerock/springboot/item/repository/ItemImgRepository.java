package org.zerock.springboot.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.springboot.item.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
}