package lossleaderproject.back.order.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.order.dto.OrderRequest;
import lossleaderproject.back.order.dto.OrderResponse;
import lossleaderproject.back.order.entity.Orders;
import lossleaderproject.back.order.entity.StoreOrder;
import lossleaderproject.back.order.repository.OrderRepository;
import lossleaderproject.back.order.repository.StoreOrderRepository;
import lossleaderproject.back.user.dto.UserRequest;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreOrderRepository storeOrderRepository;

    public Long productOrder(OrderRequest orderRequest) {
        User loginUser = userRepository.findByUserName(orderRequest.getUserName());
        if (orderRequest.isAllUseMileage() == true) {
            if (orderRequest.getPayPrice() > loginUser.getMileage()) {
                orderRequest.setUsedMileage(loginUser.getMileage());
            } else {
                orderRequest.setUsedMileage(orderRequest.getPayPrice());
            }
        }
        orderRequest.calcLastPrice(orderRequest.getPayPrice(), orderRequest.getUsedMileage());
        if (orderRequest.getLastPrice() != 0L) {
            return 0L;
        }
        Orders orders = orderRequest.toEntity();
        loginUser.restMileage(orders.getUsedMileage());
        StoreOrder saveStoreOrder = storeOrderRepository.save(new StoreOrder(loginUser));
        orders.storeOrder(saveStoreOrder);
        Orders savedOrder = orderRepository.save(orders);
        return savedOrder.getId();

    }

    @Transactional(readOnly = true)
    public OrderResponse orderDetail(Long orderId) {
        Orders orders = orderRepository.findById(orderId).get();
        StoreOrder storeOrder = orders.getStoreOrder();
        StoreOrder findStoreOrder = storeOrderRepository.findById(storeOrder.getId()).get();
        User user = findStoreOrder.getUser();
        return new OrderResponse(user.getUserName(), user.getPhoneNumber(), orders.getVisitTime(), orders.getVisitCount());
    }


}
