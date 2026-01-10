package org.mindtocode.ecommercebackend.repo;

import org.mindtocode.ecommercebackend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {

}
