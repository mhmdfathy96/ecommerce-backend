package org.mindtocode.ecommercebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releasedDate;
    private Boolean productAvailable;
    private Integer stockQuantity;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;

    @PrePersist
    protected void onCreate() {
        if (releasedDate == null) {
            releasedDate = new Date();
        }
        if (productAvailable == null) {
            productAvailable = true;
        }
        if (stockQuantity == null) {
            stockQuantity = 0;
        }
    }
}
