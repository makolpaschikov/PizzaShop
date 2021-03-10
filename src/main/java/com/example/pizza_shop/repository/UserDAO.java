package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
