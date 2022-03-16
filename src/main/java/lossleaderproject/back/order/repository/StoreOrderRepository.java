package lossleaderproject.back.order.repository;

import lossleaderproject.back.order.entity.StoreOrder;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreOrderRepository extends JpaRepository<StoreOrder, Long> {

    Page<StoreOrder> findAllByUserId(User user, Pageable pageable);

    StoreOrder findByOrdersId(Long id);

    @Query("select so.store from StoreOrder so join Orders o on o.orderNumber = :orderNumber ")
    Store findByStoreCouponContent(@Param("orderNumber") Long orderNumber);
}
