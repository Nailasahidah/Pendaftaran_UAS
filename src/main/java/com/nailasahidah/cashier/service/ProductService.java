package com.nailasahidah.cashier.service;

import com.nailasahidah.cashier.domain.Invoice;
import com.nailasahidah.cashier.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    // Product functions
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Page<Product> getProducts(int page, int size);
    Iterable<Product> getProducts();
    Product getProduct(Long id);
    Page<Product> searchProducts(String name, int page, int size);
    void uploadImage(MultipartFile image);
    // String uploadImage(Product product, byte[] imageBytes);

    // Invoice functions
    Invoice createInvoice(Invoice invoice);
    Page<Invoice> getInvoices(int page, int size);
    void addInvoiceToProduct(Long id, Invoice invoice);
    Invoice getInvoice(Long id);
    // Stats getStats();
}
