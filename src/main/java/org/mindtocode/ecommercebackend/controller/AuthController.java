package org.mindtocode.ecommercebackend.controller;

import java.io.IOException;
import java.util.UUID;

import org.mindtocode.ecommercebackend.config.GoogleOAuthProperties;
import org.mindtocode.ecommercebackend.model.User;
import org.mindtocode.ecommercebackend.model.dto.AuthResponse;
import org.mindtocode.ecommercebackend.model.dto.LoginRequest;
import org.mindtocode.ecommercebackend.model.dto.OAuthUserInfo;
import org.mindtocode.ecommercebackend.model.dto.RefreshTokenRequest;
import org.mindtocode.ecommercebackend.service.AuthService;
import org.mindtocode.ecommercebackend.service.GoogleOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.mindtocode.ecommercebackend.service.JwtService;

import jakarta.servlet.http.HttpServletResponse;

import org.mindtocode.ecommercebackend.service.IOAuthService;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @Autowired
    private GoogleOAuthProperties googleOAuthProperties;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = authService.register(user);
        String accessToken = jwtService.generateToken(registeredUser.getUsername());
        String refreshToken = jwtService.generateRefreshToken(registeredUser.getUsername());
        authService.saveRefreshToken(registeredUser.getUsername(), refreshToken);
        return ResponseEntity
                .ok(new AuthResponse(accessToken, refreshToken, registeredUser.getUsername(),
                        registeredUser.getRole().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        if (authentication.isAuthenticated()) {
            User user = authService.findByUsername(loginRequest.username());
            AuthResponse authResponse = generateTokens(user);
            return ResponseEntity.ok(authResponse);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.refreshToken();

            // Validate refresh token format and expiration
            if (!jwtService.validateRefreshToken(refreshToken)) {
                authService.deleteRefreshToken(refreshToken);
                return new ResponseEntity<>("Invalid or expired refresh token", HttpStatus.UNAUTHORIZED);
            }

            // Find user by refresh token in database
            User user = authService.findByRefreshToken(refreshToken);
            if (user == null) {
                authService.deleteRefreshToken(refreshToken);
                return new ResponseEntity<>("Refresh token not found", HttpStatus.UNAUTHORIZED);
            }

            // Generate new access token and refresh token
            AuthResponse authResponse = generateTokens(user);

            // delete old refresh token
            authService.deleteRefreshToken(refreshToken);

            return ResponseEntity
                    .ok(authResponse);
        } catch (Exception e) {
            return new ResponseEntity<>("Error refreshing token: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * OAuth2 login initiation endpoint
     * Frontend calls this endpoint to initiate OAuth2 login
     * Server redirects browser to OAuth2 provider
     * After success, oauth2Callback endpoint is called with the code and state
     * 
     * @param provider OAuth2 provider (google, github, etc.) - required as path
     *                 variable
     * @param response HttpServletResponse - response object
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/oauth2/authorize/{provider}")
    public void oauth2Authorize(@PathVariable String provider, HttpServletResponse response)
            throws IOException {
        String state = UUID.randomUUID().toString();

        // TODO: store state in Redis / DB with short TTL
        // redis.set("oauth:state:" + state, true, 5 minutes)
        // we gonna use postgres for now
        authService.saveOAuth2State(provider, state);

        String redirectUri = googleOAuthProperties.getCallbackUrl();
        System.out.println("redirectUri: " + redirectUri);
        String googleAuthUrl = getClientBaseUrl(provider) +
                "?client_id=" + getClientId(provider) +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&state=" + state +
                "&access_type=offline" +
                "&prompt=consent";

        response.sendRedirect(googleAuthUrl);
    }

    private String getClientId(String provider) {
        return switch (provider) {
            case "google" -> googleOAuthProperties.getClientId();
            default -> throw new IllegalArgumentException("Invalid provider: " + provider);
        };
    }

    private String getClientBaseUrl(String provider) {
        return switch (provider) {
            case "google" -> googleOAuthProperties.getAuthUrl();
            default -> throw new IllegalArgumentException("Invalid provider: " + provider);
        };
    }

    @GetMapping("/oauth2/callback/{provider}")
    public ResponseEntity<?> oauth2Callback(@PathVariable String provider, @RequestParam String code,
            @RequestParam String state) {
        // Validate state
        // Boolean exists = redis.get("oauth:state:" + state)
        // if (!exists) throw new RuntimeException("Invalid state");
        boolean isValidState = authService.validateOAuth2State(provider, state);
        if (!isValidState) {
            return new ResponseEntity<>("Invalid state", HttpStatus.UNAUTHORIZED);
        } else {
            authService.deleteOAuth2State(provider, state);
        }
        IOAuthService oauthService = _getOAuthService(provider);

        // 2️⃣ Exchange code for token
        String accessToken = oauthService.exchangeCode(code);

        if (accessToken == null) {
            return new ResponseEntity<>("Invalid code", HttpStatus.UNAUTHORIZED);
        }

        // Fetch user info
        OAuthUserInfo userInfo = oauthService.fetchUserInfo(accessToken);

        // Create or load user
        User user = authService.findOrCreateUser(
                provider,
                userInfo);

        // Generate access token and refresh token
        AuthResponse authResponse = generateTokens(user);
        return ResponseEntity.ok(authResponse);
    }

    private AuthResponse generateTokens(User user) {
        // Generate access token and refresh token
        String accessToken = jwtService.generateToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        // Save refresh token to database
        authService.saveRefreshToken(user.getUsername(), refreshToken);
        // return auth response
        return new AuthResponse(accessToken, refreshToken, user.getUsername(),
                user.getRole().name());
    }

    private IOAuthService _getOAuthService(String provider) {
        return switch (provider) {
            case "google" -> googleOAuthService;
            default -> throw new IllegalArgumentException("Invalid provider: " + provider);
        };
    }

}
