package org.mindtocode.springdatajpa.service;

import java.io.IOException;

import org.mindtocode.springdatajpa.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.mindtocode.springdatajpa.repo.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public Product create(Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public PagedModel<Product> getAll(int page, int size) {
        return new PagedModel<Product>(repo.findAll(PageRequest.of(page, size, Sort.by("releasedDate").descending())));
    }

    public PagedModel<Product> getAll(int page, int size, String keyword) {
        return new PagedModel<Product>(
                repo.filterByKeyword(keyword, PageRequest.of(page, size, Sort.by("releasedDate").descending())));
    }

    public Product update(Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public String delete(int productId) {
        repo.deleteById(productId);
        return "Deleted";
    }

    public Product getById(int productId) {
        return repo.findById(productId).orElse(null);
    }
}
