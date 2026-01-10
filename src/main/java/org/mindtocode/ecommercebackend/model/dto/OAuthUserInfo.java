package org.mindtocode.ecommercebackend.model.dto;

public record OAuthUserInfo(
        String sub,
        String name,
        String email,
        String picture) {
}
