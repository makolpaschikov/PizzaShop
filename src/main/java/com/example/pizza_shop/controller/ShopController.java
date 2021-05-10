package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.Basket;
import com.example.pizza_shop.domain.product.Product;
import com.example.pizza_shop.domain.product.ProductType;
import com.example.pizza_shop.domain.user.User;
import com.example.pizza_shop.service.BasketService;
import com.example.pizza_shop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ProductService productService;
    private final BasketService basketService;

    @Autowired
    public ShopController(ProductService productService, BasketService basketService) {
        this.productService = productService;
        this.basketService = basketService;
    }

    /*-------------- Shop --------------*/

    @GetMapping
    public Object getPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Basket usrBasket = basketService.getBasket(user.getUserID());
        if (usrBasket == null) model.put("basketItemsNum", 0);
        else model.put("basketItemsNum", usrBasket.getProducts().size());
        model.put("dessert", productService.getProducts(ProductType.DESSERT));
        model.put("snack", productService.getProducts(ProductType.SNACK));
        model.put("drink", productService.getProducts(ProductType.DRINK));

        List<Product> pizza = productService.getProducts(ProductType.PIZZA);
        for (Product p : pizza) {
            int cost = Integer.parseInt(p.getCost());
            p.setCost(p.getCost() + " / " + (int) (cost * 1.5));
            p.setCost(p.getCost() + " / " + (cost * 2));
        }
        model.put("pizza", pizza);
        return new ModelAndView("shop");
    }

    /*-------------- Basket --------------*/

    @GetMapping("/basket")
    public Object getBasket(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Basket usrBasket = basketService.getBasket(user.getUserID());
        if (usrBasket != null && usrBasket.getProducts().size() != 0) {
            model.put("showBtn", true);
            model.put("products", usrBasket.getProducts());
            model.put("cost", usrBasket.getCost());
        }
        return new ModelAndView("basket", model);
    }

    @PostMapping("/to_basket")
    public Object addProductToBasket(
            @AuthenticationPrincipal User user,
            @RequestParam Long productID,
            @RequestParam String crustType,
            @RequestParam String diameter
    ) {
        int cost = 0;
        Product product = productService.getProduct(productID);
        Basket basket = basketService.getBasket(user.getUserID());
        if (basket == null) basket = new Basket(user.getUserID());

        String productStr = product.toString();
        switch (crustType) {
            case "thin":
                productStr += "Тонкое тесто, ";
                break;
            case "thick":
                productStr += "Пышное тесто, ";
                break;
        }
        switch (diameter) {
            case "tf":
                productStr += "25 см. ";
                cost = Integer.parseInt(product.getCost());
                productStr += "Стоимость: " + cost + " руб.\n" ;
                break;
            case "th":
                productStr += "30 см.\n";
                cost = (int) (Integer.parseInt(product.getCost()) * 1.5);
                productStr += "Стоимость: " + cost + " руб.\n" ;
                break;
            case "thf":
                productStr += "35 см.\n";
                cost = Integer.parseInt(product.getCost()) * 2;
                productStr += "Стоимость: " + cost + " руб.\n" ;
                break;
        }
        if (product.getProductType() != ProductType.PIZZA) {
            productStr += "Стоимость: " + product.getCost() + " руб.\n";
            cost = Integer.parseInt(product.getCost());
        }

        basket.getProducts().add(productStr);
        int basketCost = basket.getCost();
        basket.setCost(basketCost + cost);
        basketService.addBasket(basket);
        return new RedirectView("/shop");
    }

    @PostMapping("/basket/order")
    public Object order(@AuthenticationPrincipal User user) {
        return new RedirectView("/shop/basket");
    }

    @PostMapping("/basket/delete")
    public Object deleteFromBasket(@AuthenticationPrincipal User user, @RequestParam String product) {
        basketService.deleteProduct(user.getUserID(), product.replace("\r", ""));
        return new RedirectView("/shop/basket");
    }

}
