package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.Basket;
import org.springframework.data.repository.CrudRepository;

public interface BasketDAO extends CrudRepository<Basket, Long> {
    Basket findByUsrID(Long id);
}
