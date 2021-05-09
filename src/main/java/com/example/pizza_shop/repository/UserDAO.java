package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.user.User;
import com.example.pizza_shop.domain.user.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRoles(UserRole roles);

}
