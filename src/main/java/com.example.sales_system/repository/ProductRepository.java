package com.example.sales_system.repository;

import com.example.sales_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Optional but recommended
public interface ProductRepository extends JpaRepository<Product, Long> {
    // no methods needed here, JpaRepository provides findById, save, etc.
}
