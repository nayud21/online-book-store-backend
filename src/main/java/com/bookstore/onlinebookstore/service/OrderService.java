package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.dto.request.OrderCreationRequest;
import com.bookstore.onlinebookstore.dto.request.OrderItemRequest;
import com.bookstore.onlinebookstore.dto.response.AdminOrderResponse;
import com.bookstore.onlinebookstore.dto.response.OrderResponse;
import com.bookstore.onlinebookstore.entity.Book;
import com.bookstore.onlinebookstore.entity.Order;
import com.bookstore.onlinebookstore.entity.OrderItem;
import com.bookstore.onlinebookstore.entity.User;
import com.bookstore.onlinebookstore.exception.AppException;
import com.bookstore.onlinebookstore.exception.ErrorCode;
import com.bookstore.onlinebookstore.mapper.OrderMapper;
import com.bookstore.onlinebookstore.respository.BookRepository;
import com.bookstore.onlinebookstore.respository.OrderRepository;
import com.bookstore.onlinebookstore.respository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    BookRepository bookRepository;
    OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(OrderCreationRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTS));

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setShippingAddress(request.getShippingAddress());
        newOrder.setStatus("PENDING");
        newOrder.setPaymentMethod(request.getPaymentMethod());
        newOrder.setPaymentStatus("UNPAID");

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequest itemRequest : request.getOrderItemRequests()){
            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

            if(book.getStockQuantity() < itemRequest.getQuantity()){
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);

            }
            book.setStockQuantity(book.getStockQuantity()- itemRequest.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPriceAtPurchase(book.getPrice());

            orderItem.setOrder(newOrder);

            orderItems.add(orderItem);

            BigDecimal totalPrice = book.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(totalPrice);
        }

        newOrder.setTotalAmount(totalAmount);
        newOrder.setOrderItems(orderItems);

        Order saveOrder = orderRepository.save(newOrder);

        return  orderMapper.toOrderResponse(saveOrder);

    }

    public Page<OrderResponse> getMyHistory(Pageable pageable){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Order> orderPage = orderRepository.findByUser_Username(username, pageable);

        return orderPage.map((orderMapper::toOrderResponse));
    }

    public OrderResponse getOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!Objects.equals(order.getUser().getUsername(),currentUsername)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return orderMapper.toOrderResponse(order);
    }
    public AdminOrderResponse getAdminOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return orderMapper.toAdminOrderResponse(order);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse getOrderByIdAdmin(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return orderMapper.toOrderResponse(order);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Page<OrderResponse> getAllOrders(Pageable pageable){
        return orderRepository.findAll(pageable).map(orderMapper::toOrderResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<AdminOrderResponse> getAllOrdersAdmin(Pageable pageable){
        return orderRepository.findAll(pageable).map(orderMapper::toAdminOrderResponse);
    }
    @PreAuthorize("hasRole('ADMIM')")
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String status){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );
        order.setStatus(status);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }
}
