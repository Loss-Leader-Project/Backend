package lossleaderproject.back.order.service;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.order.dto.*;
import lossleaderproject.back.order.entity.Orders;
import lossleaderproject.back.order.entity.StoreOrder;
import lossleaderproject.back.order.repository.OrderRepository;
import lossleaderproject.back.order.repository.StoreOrderRepository;
import lossleaderproject.back.security.auth.PrincipalDetails;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.repository.StoreRepository;
import lossleaderproject.back.user.entity.User;
import lossleaderproject.back.error.userException.UserErrorCode;
import lossleaderproject.back.error.userException.UserCustomException;
import lossleaderproject.back.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreOrderRepository storeOrderRepository;
    private final StoreRepository storeRepository;

    public ProductOrder productOrder(PrincipalDetails principalDetails,Long storeId, OrderRequest orderRequest) {
        User loginUser = userRepository.findByLoginId(principalDetails.getUsername());
        Store store = storeRepository.findById(storeId).get();
        orderRequest.payPriceofCoupon(store.getPriceOfCoupon());

        if(!loginUser.getUserName().equals(orderRequest.getUserName())) {
            throw new UserCustomException(UserErrorCode.DISMATCH_USER);
        }

        if (orderRequest.isAllUseMileage() == true) {
            if (orderRequest.getPayPrice() > loginUser.getMileage()) {
                orderRequest.setUsedMileage(loginUser.getMileage());
            } else {
                orderRequest.setUsedMileage(orderRequest.getPayPrice());
            }
        }
        orderRequest.calcLastPrice(orderRequest.getPayPrice(), orderRequest.getUsedMileage());
        if (orderRequest.getLastPrice() != 0L) {
            throw new UserCustomException(UserErrorCode.NOT_SATISFY_MONEY);
        }
        if (orderRequest.isOrderAgree() == false) {
            throw new UserCustomException(UserErrorCode.NOT_AGREE);
        }
        Orders orders = orderRequest.toEntity();
        orders.now();
        LocalDateTime now = LocalDateTime.now();
        Long orderNumber = Long.parseLong(now.getYear() + "" + now.getMonthValue() + "" + now.getDayOfMonth() + "" + now.getHour() + "" + now.getMinute() + "" + now.getSecond());
        orders.orderNumber(orderNumber);
        loginUser.restMileage(orders.getUsedMileage());
        storeOrderRepository.save(new StoreOrder(orders, store, loginUser));
        Orders savedOrder = orderRepository.save(orders);
        return new ProductOrder(orders.getId(), savedOrder.getOrderNumber(),"주문성공");
    }

    @Transactional(readOnly = true)
    public OrderRequest order( Long storeId, OrderRequest orderRequest) {
        Store store = storeRepository.findById(storeId).get();
        orderRequest.order(store.getThumbnailImage(), store.getBriefAddress(), store.getStoreName(), store.getBenefitCondition(),store.getCouponContent(), store.getPriceOfCoupon());

        return orderRequest;

    }

    @Transactional(readOnly = true)
    public Page<OrderHistory> orderHistory(PrincipalDetails principalDetails, Pageable pageable) {
        User user = userRepository.findByLoginId(principalDetails.getUsername());
        Page<StoreOrder> pageStoreOrder = storeOrderRepository.findAllByUser(user, pageable);
        return pageStoreOrder.map(storeOrder -> new OrderHistory(
                storeOrder.getOrders().getOrderDate(),
                storeOrder.getOrders().getOrderNumber(),
                storeOrder.getStore().getBriefAddress(),
                storeOrder.getStore().getId(),
                storeOrder.getStore().getStoreName(),
                storeOrder.getStore().getCouponContent(),
                storeOrder.getStore().getPriceOfCoupon(),
                storeOrder.getOrders().getIsReview()
        ));

    }

    @Transactional(readOnly = true)
    public OrderResponse orderDetail(PrincipalDetails principalDetails,Long orderNumber) {
        User user = userRepository.findByLoginId(principalDetails.getUsername());
        Orders orders = orderRepository.findByOrderNumber(orderNumber);
        StoreOrder storeOrder = storeOrderRepository.findByOrdersId(orders.getId());
        Store store = storeRepository.findByStoreName(storeOrder.getStore().getStoreName());
        return new OrderResponse(orders.getId(),store.getThumbnailImage(),store.getBriefAddress(),
                store.getStoreName(),store.getBenefitCondition(),
                store.getCouponContent(),store.getPriceOfCoupon(),user.getMileage(),user.getId(),user.getUserName(), user.getPhoneNumber(), orders.getVisitTime(), orders.getVisitCount());
    }

    @Transactional(readOnly = true)
    public PurchaseDetailsResponse purchaseDetails(PrincipalDetails principalDetails, Long storeId, Long orderNumber) {
        User user = userRepository.findByLoginId(principalDetails.getUsername());
        Store store = storeRepository.findById(storeId).get();
        Orders order = orderRepository.findByOrderNumber(orderNumber);
        PurchaseHistory purchaseHistory = new PurchaseHistory(order.getOrderDate(), order.getOrderNumber(), store.getCouponContent(), store.getPriceOfCoupon());
        PurchaseUserInfo purchaseUserInfo = new PurchaseUserInfo(user.getUserName(), user.getPhoneNumber(), order.getVisitTime(), order.getVisitCount());
        PurchaseDetailsResponse purchaseDetailsResponse = new PurchaseDetailsResponse(purchaseHistory, purchaseUserInfo);
        return purchaseDetailsResponse;
    }

    public void reviewPost(Long orderNumber){
        Orders order = orderRepository.findByOrderNumber(orderNumber);
        order.setReview(true);
        orderRepository.save(order);
    }

}
