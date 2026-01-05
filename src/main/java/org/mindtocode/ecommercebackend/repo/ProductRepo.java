package org.mindtocode.ecommercebackend.repo;

import org.mindtocode.ecommercebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "lower(p.name) LIKE lower(Concat('%', :keyword, '%')) " +
            "or lower(p.description) LIKE lower(Concat('%', :keyword, '%')) " +
            "or lower(p.brand) LIKE lower(Concat('%', :keyword, '%')) " +
            "or lower(p.category) LIKE lower(Concat('%', :keyword, '%')) ")
    Page<Product> filterByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
