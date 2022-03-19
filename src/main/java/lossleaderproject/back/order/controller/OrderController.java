
package lossleaderproject.back.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.order.dto.OrderHistory;
import lossleaderproject.back.order.dto.OrderRequest;
import lossleaderproject.back.order.dto.OrderResponse;
import lossleaderproject.back.order.dto.PurchaseDetailsResponse;
import lossleaderproject.back.order.service.OrderService;
import lossleaderproject.back.security.auth.PrincipalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "주문에 대한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;


    @ApiOperation(value = "주문하기")
    @PostMapping("/store/{storeName}")
    public ResponseEntity<String> orderProduct(@PathVariable("storeName") String storeName, @Valid @RequestBody OrderRequest orderRequest) {

        orderService.productOrder(storeName, orderRequest);

        return ResponseEntity.ok("주문 성공");
    }

    @ApiOperation("주문")
    @GetMapping("/store/{storeName}")
    public ResponseEntity<OrderRequest> order(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("storeName")String storeName,@ApiIgnore OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.order(principalDetails, storeName, orderRequest), HttpStatus.OK);

    }

    @ApiOperation(value = "총 주문내역")
    @GetMapping("/product/history")
    public ResponseEntity<Page<OrderHistory>> history(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails,@ApiIgnore @PageableDefault(size = 10) Pageable pageable) {
        Page<OrderHistory> orderHistories = orderService.orderHistory(principalDetails, pageable);
        return new ResponseEntity<>(orderHistories, HttpStatus.OK);
    }


    @ApiOperation(value = "주문 상세보기")
    @GetMapping("/detail/{orderNumber}")
    public ResponseEntity<OrderResponse> orderDetail(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable Long orderNumber) {
        OrderResponse orderResponse = orderService.orderDetail(principalDetails,orderNumber);
        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);

    }


    @ApiOperation(value = "단건 주문내역")
    @GetMapping("/purchase/{storeName}/{orderNumber}")
    public ResponseEntity<PurchaseDetailsResponse> purchaseDetails(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                   @PathVariable("storeName") String storeName,
                                                                   @PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.purchaseDetails(principalDetails,storeName,orderNumber),HttpStatus.OK);
    }

}

