package org.mindtocode.ecommercebackend.repo;

import org.mindtocode.ecommercebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
