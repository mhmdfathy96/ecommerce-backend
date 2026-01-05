package org.mindtocode.ecommercebackend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mindtocode.ecommercebackend.exceptions.ProductNotFoundException;
import org.mindtocode.ecommercebackend.exceptions.ProductOutOfStockException;
import org.mindtocode.ecommercebackend.model.Order;
import org.mindtocode.ecommercebackend.model.OrderItem;
import org.mindtocode.ecommercebackend.model.Product;
import org.mindtocode.ecommercebackend.model.dto.OrderItemRequest;
import org.mindtocode.ecommercebackend.model.dto.OrderItemResponse;
import org.mindtocode.ecommercebackend.model.dto.OrderRequest;
import org.mindtocode.ecommercebackend.model.dto.OrderResponse;
import org.mindtocode.ecommercebackend.repo.OrderRepo;
import org.mindtocode.ecommercebackend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = getOrderFromRequest(orderRequest);
        Order savedOrder = orderRepo.save(order);
        return getOrderResponse(savedOrder);
    }

    public PagedModel<OrderResponse> getOrders(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        Page<Order> orders = orderRepo.findAll(pageable);
        return new PagedModel<OrderResponse>(orders.map(this::getOrderResponse));

    }

    private Order getOrderFromRequest(OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.customerName());
        order.setEmail(orderRequest.email());
        order.setStatus("placed");
        order.setOrderId(UUID.randomUUID().toString().substring(0, 8));
        order.setOrderItems(getOrderItemsFromRequest(order, orderRequest.items()));
        return order;
    }

    private List<OrderItem> getOrderItemsFromRequest(Order order, List<OrderItemRequest> orderItemRequests) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            Product product = productRepo.findById(orderItemRequest.productId()).orElseThrow(
                    () -> new ProductNotFoundException("Product not found with id: " + orderItemRequest.productId()));
            if (product.getStockQuantity() < orderItemRequest.quantity()) {
                throw new ProductOutOfStockException(
                        "Product out of stock with name: " + product.getName());
            }
            product.setStockQuantity(product.getStockQuantity() - orderItemRequest.quantity());
            productRepo.save(product);
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(orderItemRequest.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.quantity())))
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private List<OrderItemResponse> getOrderItemResponses(List<OrderItem> orderItems) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemResponses.add(new OrderItemResponse(orderItem.getProduct().getName(), orderItem.getQuantity(),
                    orderItem.getTotalPrice()));
        }
        return orderItemResponses;
    }

    private OrderResponse getOrderResponse(Order order) {
        return new OrderResponse(order.getOrderId(), order.getCustomerName(), order.getEmail(),
                order.getOrderDate(), order.getStatus(), getOrderItemResponses(order.getOrderItems()));
    }

}
