package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.StoreFoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreFoodImageRepository extends JpaRepository<StoreFoodImage, Long> {
    List<StoreFoodImage> findAllByStoreDetailId(@Param("StoreDetailId") Long store_detail_id);

}
