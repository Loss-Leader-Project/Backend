package lossleaderproject.back.order.repository;

import lossleaderproject.back.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findByOrderNumber(Long orderNumber);
}
