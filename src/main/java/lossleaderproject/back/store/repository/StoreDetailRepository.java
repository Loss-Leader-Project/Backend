package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.StoreDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDetailRepository extends JpaRepository<StoreDetail, Long> {

    StoreDetail findByStoreId(Long storeId);
}
