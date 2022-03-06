package lossleaderproject.back.store.repository;

import lossleaderproject.back.store.entitiy.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface StoreRepository extends JpaRepository<Store, Long> {



    @Query(value = "select * from store where store.coupon_grade=0 order by store.create_date",nativeQuery = true)
    Page<Store> findAllSilverDate(Pageable pageable);

    @Query(value = "select * from store where store.coupon_grade=0 order by store.avg_star",nativeQuery = true)
    Page<Store> findAllSilverStar(Pageable pageable);

    @Query(value = "select * from store where store.coupon_grade=0 order by store.price_of_coupon ASC",nativeQuery = true)
    Page<Store> findAllSilverPriceASC(Pageable pageable);

    @Query(value = "select * from store where store.coupon_grade=0 order by store.price_of_coupon DESC",nativeQuery = true)
    Page<Store> findAllSilverPriceDESC(Pageable pageable);

    @Query(value = "select * from store where store.coupon_grade=1 order by store.create_date",nativeQuery = true)
    Page<Store> findAllGoldDate(Pageable pageable);

    @Query(value = "select * from store where store.coupon_grade=1 order by store.avg_star",nativeQuery = true)
    Page<Store> findAllGoldStar(Pageable pageable);

    @Query(value = "select * from store where store.coupon_grade=1 order by store.price_of_coupon ASC",nativeQuery = true)
    Page<Store> findAllGoldPriceASC(Pageable pageable);

    @Query(value = "select * from store where store.coupon_grade=1 order by store.price_of_coupon DESC",nativeQuery = true)
    Page<Store> findAllGoldPriceDESC(Pageable pageable);
    //Store findById(Long storeId);
}
