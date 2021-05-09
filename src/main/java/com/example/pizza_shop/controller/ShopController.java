package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.product.ProductType;
import com.example.pizza_shop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ProductService productService;

    @Autowired
    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object getPage(Map<String, Object> model) {
        model.put("pizza", productService.getProducts(ProductType.PIZZA));
        model.put("dessert", productService.getProducts(ProductType.DESSERT));
        model.put("snack", productService.getProducts(ProductType.SNACK));
        model.put("drink", productService.getProducts(ProductType.DRINK));
        return new ModelAndView("shop");
    }

}
