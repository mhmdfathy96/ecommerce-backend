package org.mindtocode.ecommercebackend.service;

import java.util.Map;

import org.mindtocode.ecommercebackend.config.GoogleOAuthProperties;
import org.mindtocode.ecommercebackend.model.dto.OAuthUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleOAuthService implements IOAuthService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GoogleOAuthProperties googleOAuthProperties;

    @Override
    public String exchangeCode(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", googleOAuthProperties.getClientId());
        body.add("client_secret", googleOAuthProperties.getClientSecret());
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", googleOAuthProperties.getCallbackUrl());

        String accessToken = (String) restTemplate.postForObject(
                googleOAuthProperties.getTokenUrl(),
                body,
                Map.class).get("access_token");
        return accessToken;
    }

    @Override
    public OAuthUserInfo fetchUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<OAuthUserInfo> response = restTemplate.exchange(
                googleOAuthProperties.getUserInfoUrl(),
                HttpMethod.GET,
                entity,
                OAuthUserInfo.class);

        return response.getBody();
    }
}
