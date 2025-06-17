package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.dto.request.ApiResponse;
import com.bookstore.onlinebookstore.dto.request.OrderCreationRequest;
import com.bookstore.onlinebookstore.dto.response.AdminOrderResponse;
import com.bookstore.onlinebookstore.dto.response.OrderResponse;
import com.bookstore.onlinebookstore.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreationRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }
    @GetMapping("/my-history")
    public ApiResponse<Page<OrderResponse>> getMyOrderHistory(Pageable pageable){
        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.getMyHistory(pageable))
                .build();
    }
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderDetail(@PathVariable Long orderId){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderById(orderId))
                .build();
    }
    @GetMapping("/admin/{orderId}")
    public ApiResponse<AdminOrderResponse> getOrderDetailAdmin(@PathVariable Long orderId){
        return ApiResponse.<AdminOrderResponse>builder()
                .result(orderService.getAdminOrderById(orderId))
                .build();
    }
    @GetMapping
    public ApiResponse<Page<OrderResponse>> getAllOrders(Pageable pageable){
        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.getAllOrders(pageable))
                .build();
    }
    @GetMapping("/admin-view")
    public ApiResponse<Page<AdminOrderResponse>> getAdminOrders(Pageable pageable){
        return ApiResponse.<Page<AdminOrderResponse>>builder()
                .result(orderService.getAllOrdersAdmin(pageable))
                .build();
    }
    @PutMapping("/{orderId}/status")
    public ApiResponse<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrderStatus(orderId,status))
                .build();
    }


}
