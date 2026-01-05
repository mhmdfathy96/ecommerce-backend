package org.mindtocode.ecommercebackend.model.dto;

import java.util.Date;
import java.util.List;

public record OrderResponse(
                String orderId,
                String customerName,
                String email,
                Date orderDate,
                String status,
                List<OrderItemResponse> items) {

}
