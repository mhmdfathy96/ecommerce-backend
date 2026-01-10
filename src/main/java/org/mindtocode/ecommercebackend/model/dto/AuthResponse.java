package org.mindtocode.ecommercebackend.model.dto;

public record AuthResponse(
                String accessToken,
                String refreshToken,
                String username,
                String role) {

}
