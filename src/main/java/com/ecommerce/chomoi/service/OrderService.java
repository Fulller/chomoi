package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.order.OrderRequest;
import com.ecommerce.chomoi.dto.order.OrderResponse;
import com.ecommerce.chomoi.dto.order.OrderStatusRequest;
import com.ecommerce.chomoi.entities.*;
import com.ecommerce.chomoi.entities.embeddedIds.OrderItemId;
import com.ecommerce.chomoi.enums.OrderStatus;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.OrderMapper;
import com.ecommerce.chomoi.repository.AddressRepository;
import com.ecommerce.chomoi.repository.OrderRepository;
import com.ecommerce.chomoi.repository.SKURepository;
import com.ecommerce.chomoi.repository.ShopRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final SecurityUtil securityUtil;
    private final SKURepository skuRepository;
    private final ShopRepository shopRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest request) {

        int totalQuantity = 0;

        BigDecimal totalPrice = BigDecimal.ZERO;

        Address address = addressRepository.findById(request.getAddressId()).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Address not found", "address-e-01")
        );

        List<OrderItem> orderItems = new ArrayList<>();

        Account buyer = securityUtil.getAccount();

        Shop shop = shopRepository.findById(request.getShopId()).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Shop not found", "shop-e-01")
        );

        Order order = Order.builder()
                .totalQuantity(0)
                .totalPrice(BigDecimal.ZERO)
                .note(request.getNote())
                .address(address)
                .buyer(buyer)
                .shop(shop)
                .build();

        order = orderRepository.save(order);

        for (OrderRequest.OrderItemDTO orderItemDTO : request.getItems()) {
            SKU sku = skuRepository.findById(orderItemDTO.getSkuId()).orElseThrow(
                    () -> new AppException(HttpStatus.NOT_FOUND, "SKU not found", "SKU-e-01")
            );
            totalQuantity = orderItemDTO.getQuantity();
            sku.setStock(sku.getStock() - orderItemDTO.getQuantity());
            skuRepository.save(sku);
            totalPrice = totalPrice.add(sku.getPrice().multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())));
            OrderItemId orderItemId = OrderItemId.builder()
                    .orderId(order.getId())
                    .skuId(sku.getId())
                    .build();
            OrderItem orderItem = OrderItem.builder()
                    .sku(sku)
                    .quantity(orderItemDTO.getQuantity())
                    .order(order)
                    .id(orderItemId)
                    .build();
            orderItems.add(orderItem);
        }
        order.setTotalQuantity(totalQuantity);
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }

    public List<OrderResponse> getAll() {
        String id = securityUtil.getAccountId();
        List<Order> orders = orderRepository.findByBuyerId(id);
        return orderMapper.toListOrderResponse(orders);
    }

    public List<OrderResponse> getByStatus(String status) {
        String id = securityUtil.getAccountId();
        OrderStatus status1 = OrderStatus.valueOf(status);
        List<Order> orders = orderRepository.findByBuyerIdAndStatus(id, status1);
        return orderMapper.toListOrderResponse(orders);
    }

    public OrderResponse get(String id) {

        if (isOrderCreator(id, securityUtil.getAccountId())) {
            throw new AppException(HttpStatus.FORBIDDEN, "You are not the creator of this order", "order-e-01");
        }

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Order not found", "order-e-2")
        );
        return orderMapper.toOrderResponse(order);
    }

    public void delete(String id) {

        if (isOrderCreator(id, securityUtil.getAccountId())) {
            throw new AppException(HttpStatus.FORBIDDEN, "You are not the creator of this order", "order-e-01");
        }
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Order not found", "order-e-2")
        );

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public boolean isOrderCreator(String orderId, String buyerId) {
        return orderRepository.findByIdAndBuyerId(orderId, buyerId).isEmpty();
    }


    public List<OrderResponse> listOrderOfShop() {
        String shopId = securityUtil.getShop().getId();

        List<Order> orders = orderRepository.findByShopId(shopId);

        return orderMapper.toListOrderResponse(orders);
    }

    public List<OrderResponse> getListOrderByStatusOfShop(String status) {
        String shopId = securityUtil.getShop().getId();
        OrderStatus status1 = OrderStatus.valueOf(status);
        List<Order> orders = orderRepository.findByShopIdAndStatus(shopId, status1);

        return orderMapper.toListOrderResponse(orders);
    }

    public OrderResponse updateOrderStatus(String id, OrderStatusRequest request) {

        List<OrderStatus> statuses = List.of(
                OrderStatus.PENDING,
                OrderStatus.CONFIRMED,
                OrderStatus.DELIVERING,
                OrderStatus.CANCELLED
        );

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND, "Order not found", "order-e-2")
        );
        OrderStatus status = request.getStatus();

        if (!statuses.contains(status)) {
            throw new AppException(HttpStatus.FORBIDDEN, "You don't have permission to edit orders to this status.", "order-e-3");
        }

        order.setStatus(status);

        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }
}
