package lossleaderproject.back.order.repository;

import lossleaderproject.back.order.entity.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Orders findByOrderNumber(int orderNumber);
}
