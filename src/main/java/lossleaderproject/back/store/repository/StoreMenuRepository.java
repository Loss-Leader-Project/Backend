package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
    List<StoreMenu> findAllByStoreDetailId(@Param("StoreDetailId") Long store_detail_id);
}
