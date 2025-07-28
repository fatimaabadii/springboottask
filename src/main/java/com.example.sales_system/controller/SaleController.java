package com.example.sales_system.controller;

import com.example.sales_system.model.Sale;
import com.example.sales_system.model.SaleTransaction;
import com.example.sales_system.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @PutMapping("/{id}/transactions")
    public ResponseEntity<Sale> updateSaleTransactions(
            @PathVariable Long id,
            @RequestBody List<SaleTransaction> transactions) {

        return saleService.updateSaleTransactions(id, transactions)
                .map(updated -> ResponseEntity.ok(updated))
                .orElse(ResponseEntity.notFound().build());
    }
}
