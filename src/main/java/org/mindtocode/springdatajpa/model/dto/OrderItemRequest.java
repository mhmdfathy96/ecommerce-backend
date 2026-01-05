package org.mindtocode.springdatajpa.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity) {

}
