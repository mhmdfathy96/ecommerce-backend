package org.mindtocode.ecommercebackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.mindtocode.ecommercebackend.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
