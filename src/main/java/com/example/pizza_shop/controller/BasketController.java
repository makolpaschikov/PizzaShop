package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.Basket;
import com.example.pizza_shop.domain.user.User;
import com.example.pizza_shop.service.BasketService;
import com.example.pizza_shop.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/shop/basket")
public class BasketController {
    private final BasketService basketService;
    private final MailService mailService;

    @Autowired
    public BasketController(BasketService basketService, MailService mailService) {
        this.basketService = basketService;
        this.mailService = mailService;
    }

    @GetMapping()
    public Object getBasket(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Basket usrBasket = basketService.getBasket(user.getUserID());
        if (usrBasket != null && usrBasket.getProducts().size() != 0) {
            model.put("showBtn", true);
            model.put("products", usrBasket.getProducts());
            model.put("cost", usrBasket.getCost());
        }
        return new ModelAndView("basket", model);
    }

    @PostMapping("/order")
    public Object order(@AuthenticationPrincipal User user, @RequestParam String address) {
        Basket basket = basketService.getBasket(user.getUserID());
        mailService.sendOrder(user, basket, address + "\n");
        basketService.deleteBasket(basket.getBasketID());
        return new RedirectView("/shop");
    }

    @PostMapping("/delete")
    public Object deleteFromBasket(@AuthenticationPrincipal User user, @RequestParam String product) {
        basketService.deleteProduct(user.getUserID(), product.replace("\r", ""));
        return new RedirectView("/shop/basket");
    }
}
