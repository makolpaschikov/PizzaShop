package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.product.Product;
import com.example.pizza_shop.domain.product.ProductType;
import com.example.pizza_shop.domain.user.User;
import com.example.pizza_shop.domain.user.UserRole;
import com.example.pizza_shop.service.product.ProductService;
import com.example.pizza_shop.service.SessionService;
import com.example.pizza_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/admin_panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final SessionService sessionService;
    private final ProductService productService;

    @Autowired
    public AdminController(UserService userService, SessionService sessionService, ProductService productService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.productService = productService;
    }

    @GetMapping
    public Object getPage() {
        return new ModelAndView("admin_panel");
    }

    /*-------------- Users --------------*/

    @GetMapping("/users")
    public Object getUsers(Map<String, Object> model) {
        model.put("user_list", userService.getOnlyUsers());
        model.put("admin_list", userService.getOnlyAdmins());
        return new ModelAndView("user_list", model);
    }

    @PostMapping("/add_admin")
    public Object addAdmin(User user) {
        Set<UserRole> roles = new HashSet<>();
        Collections.addAll(roles, UserRole.ADMIN, UserRole.USER);
        userService.addUser(user, roles);
        return new RedirectView("/admin_panel/users");
    }

    @PostMapping("/users/delete_user")
    public Object deleteUser(User user) {
        userService.deleteUser(user);
        sessionService.closeSession(user);
        return new RedirectView("/admin_panel/users");
    }

    /*-------------- Products --------------*/

    @GetMapping("/add_product")
    public Object getProductForm() {
        return new ModelAndView("create_product");
    }

    @PostMapping("/add_product")
    public Object addProduct(
            @RequestParam String name,
            @RequestParam String cost,
            @RequestParam String composition,
            @RequestParam MultipartFile imgFile,
            @RequestParam String type
    ) {
        Product product = new Product(name, Double.parseDouble(cost), composition, ProductType.valueOf(type));
        productService.addProduct(product, imgFile);
        return new RedirectView("/admin_panel/add_product");
    }

    @GetMapping("/products")
    public Object getProducts(Map<String, Object> model) {
        model.put("pizza", productService.getProducts(ProductType.PIZZA));
        model.put("dessert", productService.getProducts(ProductType.DESSERT));
        model.put("snack", productService.getProducts(ProductType.SNACK));
        model.put("drink", productService.getProducts(ProductType.DRINK));
        return new ModelAndView("product_list", model);
    }

    @PostMapping("/delete_product")
    public Object deleteProduct(@RequestParam Long id, @RequestParam String type) {
        productService.deleteProduct(id, ProductType.valueOf(type));
        return new RedirectView("/admin_panel/products");
    }

    @PostMapping("/delete_products")
    public Object deleteProducts() {
        productService.deleteAll();
        return new RedirectView("/admin_panel/products");
    }

}
