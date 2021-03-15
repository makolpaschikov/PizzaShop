package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRoles(UserRole roles);

    @Query(value = "DELETE FROM spring_session ss WHERE ss.principal_name=:username", nativeQuery = true)
    void deleteSessionByUsername(@Param(value = "username") String username);
}
