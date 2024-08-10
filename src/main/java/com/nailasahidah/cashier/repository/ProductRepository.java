package com.nailasahidah.cashier.repository;

import com.nailasahidah.cashier.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, ListCrudRepository<Product, Long> {
    Page<Product>findByNameContaining(String name, Pageable pageable);
}
