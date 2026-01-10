package org.mindtocode.ecommercebackend.service;

import org.mindtocode.ecommercebackend.model.dto.OAuthUserInfo;

public interface IOAuthService {
    String exchangeCode(String code);
    OAuthUserInfo fetchUserInfo(String accessToken);
}