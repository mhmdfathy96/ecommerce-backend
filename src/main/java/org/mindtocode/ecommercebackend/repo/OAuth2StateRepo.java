package org.mindtocode.ecommercebackend.repo;

import java.util.Optional;

import org.mindtocode.ecommercebackend.model.OAuth2State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OAuth2StateRepo extends JpaRepository<OAuth2State, String> {

    @Query("SELECT o FROM OAuth2State o WHERE o.provider = :provider AND o.state = :state")
    Optional<OAuth2State> findProviderAndState(String provider, String state);

}
