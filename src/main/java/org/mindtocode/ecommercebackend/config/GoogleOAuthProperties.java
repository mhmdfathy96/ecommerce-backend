package org.mindtocode.ecommercebackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "google.oauth")
@Getter
@Setter
public class GoogleOAuthProperties {
    private String authUrl;
    private String tokenUrl;
    private String userInfoUrl;
    private String callbackEndpoint;
    private String clientId;
    private String clientSecret;

    public String getCallbackUrl() {
        // get base url of application
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return baseUrl + callbackEndpoint;
    }
}
