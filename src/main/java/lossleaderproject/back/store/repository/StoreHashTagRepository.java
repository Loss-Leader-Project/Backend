package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.StoreHashTag;
import lossleaderproject.back.store.entitiy.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreHashTagRepository extends JpaRepository<StoreHashTag, Long> {
    List<StoreHashTag> findAllByStoreDetailId(@Param("StoreDetailId") Long store_detail_id);
}
