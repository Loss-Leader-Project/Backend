package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.StoreFoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreFoodImageRepository extends JpaRepository<StoreFoodImage, Long> {
    @Query(value = "select sf.name,sf.id,sf.image,sf.store_detail_id from store_detail right outer join store_food_image sf on store_detail.id = sf.store_detail_id  WHERE sf.store_detail_id = ?1" ,nativeQuery = true)
    List<StoreFoodImage> findOneByDetailId(@Param("StoreDetailId") Long store_detail_id);

}
