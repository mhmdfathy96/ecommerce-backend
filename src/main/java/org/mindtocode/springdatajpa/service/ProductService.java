package org.mindtocode.springdatajpa.service;

import org.mindtocode.springdatajpa.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.mindtocode.springdatajpa.repo.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public Product create(Product product) {
        return repo.save(product);
    }

    public Page<Product> getAll(int page, int size) {
        return  repo.findAll(PageRequest.of(page, size));
    }

    public Product update(Product product) {
        return repo.save(product);
    }

    public String delete(int productId) {
        repo.deleteById(productId);
        return "Deleted";
    }
}
