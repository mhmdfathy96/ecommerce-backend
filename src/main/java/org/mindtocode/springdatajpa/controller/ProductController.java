package org.mindtocode.springdatajpa.controller;
import org.mindtocode.springdatajpa.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.mindtocode.springdatajpa.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("product")
    Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("product")
    Page<Product> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return productService.getAll(page, size);
    }

    @PutMapping("product")
    Product update(Product product) {
        return productService.update(product);
    }

    @DeleteMapping("product/{productId}")
    String delete(@PathVariable int productId) {
        return productService.delete(productId);
    }
}
