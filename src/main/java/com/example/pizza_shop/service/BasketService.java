package com.example.pizza_shop.service;

import com.example.pizza_shop.domain.Basket;
import com.example.pizza_shop.repository.BasketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    private final BasketDAO basketDAO;

    @Autowired
    public BasketService(BasketDAO basketDAO) {
        this.basketDAO = basketDAO;
    }

    public void addBasket(Basket basket) {
        basketDAO.save(basket);
    }

    public Basket getBasket(Long usrID) {
        return basketDAO.findByUsrID(usrID);
    }

    public void updateBasket(Basket basket) {
        basketDAO.save(basket);
    }

    public void deleteProduct(Long usrID, String product) {
        Basket usrBasket = basketDAO.findByUsrID(usrID);
        usrBasket.getProducts().remove(product);
        updateBasket(usrBasket);
    }

    public void deleteBasket(Long bktID) {
        basketDAO.deleteById(bktID);
    }
}
