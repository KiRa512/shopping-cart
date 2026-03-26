package com.kira.shopping_cart.model;

import jakarta.persistence.*;

import lombok.*;

import java.sql.Blob;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
