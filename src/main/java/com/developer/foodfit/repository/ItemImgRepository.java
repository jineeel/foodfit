package com.developer.foodfit.repository;

import com.developer.foodfit.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {
    List<ItemImg> findAllByItemId(Long itemId);
    List<ItemImg> findItemImgByItemId(Long itemId);
    ItemImg findByItemId(Long itemId);
    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);
    List<ItemImg> findTop10ByOrderByIdDesc();
    List<ItemImg> findByRepImgYn(String repImgYn);


}
