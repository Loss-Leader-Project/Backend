package lossleaderproject.back.order.repository;

import lossleaderproject.back.order.entity.StoreOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreOrderRepository extends JpaRepository<StoreOrder, Long> {

    Page<StoreOrder> findAllByUserId(Long id, Pageable pageable);

    StoreOrder findByOrdersId(Long id);
}
