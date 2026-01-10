package org.mindtocode.ecommercebackend.service;

import org.mindtocode.ecommercebackend.model.RefreshToken;
import org.mindtocode.ecommercebackend.model.User;
import org.mindtocode.ecommercebackend.model.dto.OAuthUserInfo;
import org.mindtocode.ecommercebackend.repo.OAuth2StateRepo;
import org.mindtocode.ecommercebackend.repo.RefreshTokenRepo;
import org.mindtocode.ecommercebackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    @Autowired
    private OAuth2StateRepo oauth2StateRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void saveRefreshToken(String username, String refreshToken) {
        User user = userRepo.findByUsername(username);
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUser(user);
        refreshTokenRepo.save(refreshTokenEntity);
    }

    public User findByRefreshToken(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepo.findById(refreshToken).orElse(null);
        return refreshTokenEntity != null ? refreshTokenEntity.getUser() : null;
    }

    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepo.deleteById(refreshToken);
    }

    /**
     * Find existing user or create new user from OAuth2 authentication
     * 
     * @param username Username (typically email from OAuth2 provider)
     * @param name     Full name from OAuth2 provider
     * @param email    Email from OAuth2 provider
     * @return User entity
     */
    public User findOrCreateOAuth2User(String username) {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            // Create new user for OAuth2 authentication
            user = new User();
            user.setUsername(username);
            user = userRepo.save(user);
        }

        return user;
    }

    public void saveOAuth2State(String provider, String state) {
        OAuth2State oauth2State = new OAuth2State();
        oauth2State.setProvider(provider);
        oauth2State.setState(state);
        oauth2StateRepo.save(oauth2State);
    }

    public boolean validateOAuth2State(String provider, String state) {
        OAuth2State oauth2State = oauth2StateRepo.findProviderAndState(provider, state).orElse(null);
        return oauth2State != null;
    }

    public void deleteOAuth2State(String provider, String state) {
        oauth2StateRepo.deleteById(state);
    }

    public User findOrCreateUser(String provider, OAuthUserInfo userInfo) {
        User user = userRepo.findByUsername(userInfo.email());
        if (user == null) {
            user = new User();
            user.setUsername(userInfo.email());
            user.setProvider(provider);
            user = userRepo.save(user);
        }
        return user;
    }

}
