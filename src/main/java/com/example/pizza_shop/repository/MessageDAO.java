package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface MessageDAO extends CrudRepository<Message, Long> {
    void deleteById(@NonNull Long id);
}
