package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
