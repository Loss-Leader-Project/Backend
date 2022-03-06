//package lossleaderproject.back.order.service;
//
//import lombok.RequiredArgsConstructor;
//import lossleaderproject.back.order.dto.OrderHistory;
//import lossleaderproject.back.order.dto.OrderRequest;
//import lossleaderproject.back.order.dto.OrderResponse;
//import lossleaderproject.back.order.entity.Orders;
//import lossleaderproject.back.order.entity.StoreOrder;
//import lossleaderproject.back.order.repository.OrderRepository;
//import lossleaderproject.back.order.repository.StoreOrderRepository;
//import lossleaderproject.back.store.entitiy.Coupon;
//import lossleaderproject.back.store.entitiy.Store;
//import lossleaderproject.back.store.repository.StoreRepository;
//import lossleaderproject.back.user.entity.User;
//import lossleaderproject.back.user.repository.UserRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class OrderService {
//    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;
//    private final StoreOrderRepository storeOrderRepository;
//    private final StoreRepository storeRepository;
//
//    public Long productOrder(Long storeId, OrderRequest orderRequest) {
//        User loginUser = userRepository.findByUserName(orderRequest.getUserName());
//        Store store = storeRepository.findById(storeId).get();
//        orderRequest.payPriceofCoupon(store.getCoupon().getPriceOfCoupon());
//        if (orderRequest.isAllUseMileage() == true) {
//            if (orderRequest.getPayPrice() > loginUser.getMileage()) {
//                orderRequest.setUsedMileage(loginUser.getMileage());
//            } else {
//                orderRequest.setUsedMileage(orderRequest.getPayPrice());
//            }
//        }
//        orderRequest.calcLastPrice(orderRequest.getPayPrice(), orderRequest.getUsedMileage());
//        if (orderRequest.getLastPrice() != 0L) {
//            return 0L;
//        }
//        Orders orders = orderRequest.toEntity();
//        loginUser.restMileage(orders.getUsedMileage());
//        storeOrderRepository.save(new StoreOrder(orders, store, loginUser));
//        Orders savedOrder = orderRepository.save(orders);
//        return savedOrder.getId();
//    }
//
//    @Transactional(readOnly = true)
//    public OrderRequest order(Long userId, Long storeId, OrderRequest orderRequest) {
//        User user = userRepository.findById(userId).get();
//        Store store = storeRepository.findById(storeId).get();
//        Coupon coupon = store.getCoupon();
//        orderRequest.order(store.getBriefAddress(), store.getStoreName(), coupon.getCouponContent(), coupon.getPriceOfCoupon(), user.getUserName(), user.getPhoneNumber());
//
//        return orderRequest;
//
//    }
//
//    @Transactional(readOnly = true)
//    public Page<OrderHistory> orderHistory(Long userId, Pageable pageable) {
//        Page<StoreOrder> pageStoreOrder = storeOrderRepository.findAllByUserId(userId, pageable);
//
//        System.out.println("pageStoreOrder.getContent() = " + pageStoreOrder.getContent());
//        return pageStoreOrder.map(storeOrder -> new OrderHistory(
//                storeOrder.getUser().getUserName(), storeOrder.getUser().getMileage(), storeOrder.getStore().getCreateDate(),
//                storeOrder.getOrders().getOrderNumber(), storeOrder.getStore().getBriefAddress(),
//                storeOrder.getStore().getStoreName(), storeOrder.getStore().getCoupon().getCouponContent(),
//                storeOrder.getStore().getCoupon().getPriceOfCoupon()
//        ));
//
//    }
//
//    @Transactional(readOnly = true)
//    public OrderResponse orderDetail(Long orderId) {
//        Orders orders = orderRepository.findById(orderId).get();
//        StoreOrder storeOrder = storeOrderRepository.findByOrdersId(orders.getId());
//        StoreOrder findStoreOrder = storeOrderRepository.findById(storeOrder.getId()).get();
//        User user = findStoreOrder.getUser();
//        return new OrderResponse(user.getUserName(), user.getPhoneNumber(), orders.getVisitTime(), orders.getVisitCount());
//    }
//
//
//}
