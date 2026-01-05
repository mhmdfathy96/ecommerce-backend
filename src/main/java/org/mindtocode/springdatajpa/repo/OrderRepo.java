package org.mindtocode.springdatajpa.repo;

import org.mindtocode.springdatajpa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

}
