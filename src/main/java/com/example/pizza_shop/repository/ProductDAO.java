package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDAO extends CrudRepository<Product, Long> {
}
