package com.example.sales_system.model;

import javax.persistence.*;

import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private LocalDateTime creationDate;
}
