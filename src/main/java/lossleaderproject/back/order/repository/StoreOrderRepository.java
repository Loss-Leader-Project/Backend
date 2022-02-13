package lossleaderproject.back.order.repository;

import lossleaderproject.back.order.entity.StoreOrder;
import lossleaderproject.back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreOrderRepository extends JpaRepository<StoreOrder, Long> {

}
