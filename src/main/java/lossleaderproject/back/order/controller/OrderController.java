
package lossleaderproject.back.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lossleaderproject.back.order.dto.*;
import lossleaderproject.back.order.service.OrderService;
import lossleaderproject.back.security.auth.PrincipalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @PostMapping("/store")
    public ResponseEntity<ProductOrder> orderProduct(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails , @RequestParam Long storeId, @Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.productOrder(principalDetails,storeId, orderRequest));
    }

    @ApiOperation("주문")
    @GetMapping("/store")
    public ResponseEntity<OrderRequest> order(@RequestParam Long storeId,@ApiIgnore OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.order(storeId, orderRequest), HttpStatus.OK);

    }

    @ApiOperation(value = "총 주문내역")
    @GetMapping("/product/history")
    public ResponseEntity<Page<OrderHistory>> history(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails,@ApiIgnore @PageableDefault(size = 10) Pageable pageable) {
        Page<OrderHistory> orderHistories = orderService.orderHistory(principalDetails, pageable);
        return new ResponseEntity<>(orderHistories, HttpStatus.OK);
    }


    @ApiOperation(value = "주문 상세보기")
    @GetMapping("/detail")
    public ResponseEntity<OrderResponse> orderDetail(@AuthenticationPrincipal PrincipalDetails principalDetails,@RequestParam("orderNumber") Long orderNumber) {
        OrderResponse orderResponse = orderService.orderDetail(principalDetails,orderNumber);
        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);

    }


    @ApiOperation(value = "단건 주문내역")
    @GetMapping("/purchase/{orderNumber}")
    public ResponseEntity<PurchaseDetailsResponse> purchaseDetails(@ApiIgnore @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                   @RequestParam("storeId") Long storeId,
                                                                   @PathVariable("orderNumber") Long orderNumber) {
        return new ResponseEntity<>(orderService.purchaseDetails(principalDetails,storeId,orderNumber),HttpStatus.OK);
    }

}

