package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
    @Query(value = "select sm.name,sm.id,sm.price,sm.store_detail_id from store_detail right outer join store_menu sm on store_detail.id = sm.store_detail_id  WHERE sm.store_detail_id = ?1" ,nativeQuery = true)
    List<StoreMenu> findOneByDetailId(@Param("StoreDetailId") Long store_detail_id);
}
