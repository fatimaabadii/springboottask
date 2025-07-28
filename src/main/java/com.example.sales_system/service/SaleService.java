package com.example.sales_system.service;


import com.example.sales_system.model.Client;
import com.example.sales_system.model.Product;
import com.example.sales_system.model.Sale;
import com.example.sales_system.model.SaleTransaction;
import com.example.sales_system.repository.ClientRepository;
import com.example.sales_system.repository.ProductRepository;
import com.example.sales_system.repository.SaleRepository;
import com.example.sales_system.repository.SaleTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleTransactionRepository saleTransactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    // Fetch all sales
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Create new sale with transactions
    public Sale createSale(Sale sale) {
        sale.setCreationDate(LocalDateTime.now());

        // Validate client exists
        Client client = clientRepository.findById(sale.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        sale.setClient(client);

        // For each transaction, link product and sale, calculate total
        double total = 0.0;
        for (SaleTransaction tx : sale.getTransactions()) {
            Product product = productRepository.findById(tx.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            tx.setProduct(product);
            tx.setSale(sale);
            total += tx.getQuantity() * tx.getPrice();
        }
        sale.setTotal(total);

        return saleRepository.save(sale);
    }

    // Update single sale transaction (quantity & price)
    public Optional<SaleTransaction> updateTransaction(Long transactionId, Integer quantity, Double price) {
        return saleTransactionRepository.findById(transactionId).map(transaction -> {
            // Log old state
            System.out.println("Updating SaleTransaction ID: " + transactionId +
                    " | Old Qty: " + transaction.getQuantity() +
                    ", Old Price: " + transaction.getPrice());

            transaction.setQuantity(quantity);
            transaction.setPrice(price);

            // Log new state
            System.out.println("Updated SaleTransaction ID: " + transactionId +
                    " | New Qty: " + transaction.getQuantity() +
                    ", New Price: " + transaction.getPrice());

            saleTransactionRepository.save(transaction);

            // Recalculate total for the parent sale
            Sale sale = transaction.getSale();
            double newTotal = sale.getTransactions().stream()
                    .mapToDouble(t -> t.getQuantity() * t.getPrice())
                    .sum();
            sale.setTotal(newTotal);
            saleRepository.save(sale);

            return transaction;
        });
    }

    // Bulk update transactions of a sale (replace all with new list)
    public Optional<Sale> updateSaleTransactions(Long saleId, List<SaleTransaction> updatedTransactions) {
        return saleRepository.findById(saleId).map(existingSale -> {
            // Clear old transactions to remove them from DB (because of orphanRemoval = true)
            existingSale.getTransactions().clear();

            double total = 0.0;
            for (SaleTransaction tx : updatedTransactions) {
                Product product = productRepository.findById(tx.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found: " + tx.getProduct().getId()));

                tx.setProduct(product);
                tx.setSale(existingSale);
                existingSale.getTransactions().add(tx);

                total += tx.getQuantity() * tx.getPrice();

                // Log the change
                System.out.println("Updated SaleTransaction: productId=" + product.getId()
                        + ", quantity=" + tx.getQuantity() + ", price=" + tx.getPrice());
            }

            existingSale.setTotal(total);
            return saleRepository.save(existingSale);
        });
    }


}
