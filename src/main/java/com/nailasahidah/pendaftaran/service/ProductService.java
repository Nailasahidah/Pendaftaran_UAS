package com.nailasahidah.pendaftaran.service;

import com.nailasahidah.pendaftaran.domain.Invoice;
import com.nailasahidah.pendaftaran.domain.Product;
import com.nailasahidah.pendaftaran.domain.Stats;
import org.springframework.data.domain.Page;

public interface ProductService {
    // Product functions
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Page<Product> getProducts(int page, int size);
    Iterable<Product> getProducts();
    Product getProduct(Long id);
    Page<Product> searchProducts(String name, int page, int size);

    // Invoice functions
    Invoice createInvoice(Invoice invoice);
    Page<Invoice> getInvoices(int page, int size);
    void addInvoiceToProduct(Long id, Invoice invoice);
    Invoice getInvoice(Long id);
    // Stats getStats();
}
