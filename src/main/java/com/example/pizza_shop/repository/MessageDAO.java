package com.example.pizza_shop.repository;

import com.example.pizza_shop.domain.package4test.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageDAO extends CrudRepository<Message, Long> {
}
