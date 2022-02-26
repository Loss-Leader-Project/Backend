package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.StoreHashTag;
import lossleaderproject.back.store.entitiy.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreHashTagRepository extends JpaRepository<StoreHashTag, Long> {
    @Query(value = "select shg.name,shg.id,shg.store_detail_id from store_detail right outer join store_hash_tag shg on store_detail.id = shg.store_detail_id  WHERE shg.store_detail_id = ?1" ,nativeQuery = true)
    List<StoreHashTag> findOneByDetailId(@Param("StoreDetailId") Long store_detail_id);
}
