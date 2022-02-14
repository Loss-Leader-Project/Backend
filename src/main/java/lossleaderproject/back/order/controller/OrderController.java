package lossleaderproject.back.order.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.order.dto.OrderRequest;
import lossleaderproject.back.order.dto.OrderResponse;
import lossleaderproject.back.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<String> orderProduct(@Valid @RequestBody OrderRequest orderRequest) {

        if(orderRequest.isOrderAgree() == false) {
            return ResponseEntity.badRequest().body("동의를 해주셔야됩니다.");
        }
        Long orderId = orderService.productOrder(orderRequest);
        if(orderId == 0) {
            return ResponseEntity.badRequest().body("금액이 부족하여 주문에 실패하였습니다.");
        }
        return ResponseEntity.ok("주문 성공");
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<OrderResponse> orderDetail(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.orderDetail(orderId);
        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);

    }
}
