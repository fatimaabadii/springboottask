package com.example.sales_system.repository;

import com.example.sales_system.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Basic CRUD from JpaRepository
}
