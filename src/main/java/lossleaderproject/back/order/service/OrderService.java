package lossleaderproject.back.order.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.order.dto.*;
import lossleaderproject.back.order.entity.Orders;
import lossleaderproject.back.order.entity.StoreOrder;
import lossleaderproject.back.order.repository.OrderRepository;
import lossleaderproject.back.order.repository.StoreOrderRepository;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.repository.StoreRepository;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreOrderRepository storeOrderRepository;
    private final StoreRepository storeRepository;

    public Long productOrder(Long storeId, OrderRequest orderRequest) {
        User loginUser = userRepository.findByUserName(orderRequest.getUserName());
        Store store = storeRepository.findById(storeId).get();
        orderRequest.payPriceofCoupon(store.getPriceOfCoupon());
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
        orders.now();
        loginUser.restMileage(orders.getUsedMileage());
        storeOrderRepository.save(new StoreOrder(orders, store, loginUser));
        Orders savedOrder = orderRepository.save(orders);
        return savedOrder.getId();
    }

    @Transactional(readOnly = true)
    public OrderRequest order(Long userId, Long storeId, OrderRequest orderRequest) {
        User user = userRepository.findById(userId).get();
        Store store = storeRepository.findById(storeId).get();
        orderRequest.order(store.getBriefAddress(), store.getStoreName(), store.getCouponContent(), store.getPriceOfCoupon(), user.getUserName(), user.getPhoneNumber());

        return orderRequest;

    }

    @Transactional(readOnly = true)
    public Page<OrderHistory> orderHistory(Long userId, Pageable pageable) {
        Page<StoreOrder> pageStoreOrder = storeOrderRepository.findAllByUserId(userId, pageable);
        return pageStoreOrder.map(storeOrder -> new OrderHistory(
                storeOrder.getUser().getUserName(), storeOrder.getUser().getMileage(), storeOrder.getOrders().getOrderDate(),
                storeOrder.getOrders().getOrderNumber(), storeOrder.getStore().getBriefAddress(),
                storeOrder.getStore().getStoreName(), storeOrder.getStore().getCouponContent(),
                storeOrder.getStore().getPriceOfCoupon()
        ));

    }

    @Transactional(readOnly = true)
    public OrderResponse orderDetail(Long orderId) {
        Orders orders = orderRepository.findById(orderId).get();
        StoreOrder storeOrder = storeOrderRepository.findByOrdersId(orders.getId());
        StoreOrder findStoreOrder = storeOrderRepository.findById(storeOrder.getId()).get();
        User user = findStoreOrder.getUser();
        return new OrderResponse(user.getUserName(), user.getPhoneNumber(), orders.getVisitTime(), orders.getVisitCount());
    }

    @Transactional(readOnly = true)
    public PurchaseDetailsResponse purchaseDetails(Long userId, Long storeId, Long orderNumber) {
        User user = userRepository.findById(userId).get();
        Store store = storeRepository.findById(storeId).get();
        Orders order = orderRepository.findByOrderNumber(orderNumber);
        PurchaseHistory purchaseHistory = new PurchaseHistory(order.getOrderDate(), order.getOrderNumber(), store.getCouponContent(), store.getPriceOfCoupon());
        PurchaseUserInfo purchaseUserInfo = new PurchaseUserInfo(user.getUserName(), user.getPhoneNumber(), order.getVisitTime(), order.getVisitCount());
        PurchaseDetailsResponse purchaseDetailsResponse = new PurchaseDetailsResponse(purchaseHistory,purchaseUserInfo);

        return purchaseDetailsResponse;

    }

}
