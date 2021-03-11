package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByRoles(UserRole roles);
}
