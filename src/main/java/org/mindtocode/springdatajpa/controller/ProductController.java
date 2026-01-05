package org.mindtocode.springdatajpa.controller;

import java.io.IOException;

import org.mindtocode.springdatajpa.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.mindtocode.springdatajpa.service.ProductService;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("product")
    ResponseEntity<?> create(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            return ResponseEntity.ok(productService.create(product, imageFile));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("products")
    ResponseEntity<PagedModel<Product>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    @GetMapping("products/search")
    ResponseEntity<PagedModel<Product>> getByKeyword(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam String keyword) {
        return ResponseEntity.ok(productService.getAll(page, size, keyword));
    }

    @PutMapping("product")
    ResponseEntity<?> update(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            return ResponseEntity.ok(productService.update(product, imageFile));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("product/{productId}")
    String delete(@PathVariable int productId) {
        return productService.delete(productId);
    }

    @GetMapping("product/{productId}")
    ResponseEntity<Product> getById(@PathVariable int productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("product/{productId}/image")
    ResponseEntity<byte[]> getImage(@PathVariable int productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.getImageData());
    }
}
