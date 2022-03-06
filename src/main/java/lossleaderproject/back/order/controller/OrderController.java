//package lossleaderproject.back.order.controller;
//
//import lombok.RequiredArgsConstructor;
//import lossleaderproject.back.order.dto.OrderHistory;
//import lossleaderproject.back.order.dto.OrderRequest;
//import lossleaderproject.back.order.dto.OrderResponse;
//import lossleaderproject.back.order.service.OrderService;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RestController
//@RequiredArgsConstructor
//public class OrderController {
//    private final OrderService orderService;
//
//
//    @PostMapping("/product/{userId}/store/{storeId}")
//    public ResponseEntity<String> orderProduct(@PathVariable("userId") Long userId, @PathVariable("storeId") Long storeId, @Valid @RequestBody OrderRequest orderRequest) {
//
//        if (orderRequest.isOrderAgree() == false) {
//            return ResponseEntity.badRequest().body("동의를 해주셔야됩니다.");
//        }
//        Long orderId = orderService.productOrder(storeId, orderRequest);
//        if (orderId == 0) {
//            return ResponseEntity.badRequest().body("금액이 부족하여 주문에 실패하였습니다.");
//        }
//        return ResponseEntity.ok("주문 성공");
//    }
//
//    @GetMapping("/product/{userId}/store/{storeId}")
//    public ResponseEntity<OrderRequest> order(@PathVariable("userId") Long userId, @PathVariable("storeId") Long storeId, OrderRequest orderRequest) {
//        return new ResponseEntity<>(orderService.order(userId, storeId, orderRequest), HttpStatus.OK);
//
//    }
//
//    @GetMapping("my-page/{userId}/product-history")
//    public ResponseEntity<Page<OrderHistory>> history(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
//        Page<OrderHistory> orderHistories = orderService.orderHistory(userId, pageable);
//        return new ResponseEntity<>(orderHistories, HttpStatus.OK);
//    }
//
//
//    @GetMapping("/product/detail/{orderId}")
//    public ResponseEntity<OrderResponse> orderDetail(@PathVariable Long orderId) {
//        OrderResponse orderResponse = orderService.orderDetail(orderId);
//        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);
//
//    }
//}
