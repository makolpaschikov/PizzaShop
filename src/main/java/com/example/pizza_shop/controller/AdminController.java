package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import com.example.pizza_shop.service.ProductService;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    //======================
    // USERS
    //======================
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

    //======================
    // PRODUCTS
    //======================
    @GetMapping("/add_product")
    public Object getProductForm() {
        return new ModelAndView("create_product");
    }

    @PostMapping("/add_product")
    public Object addProduct(@RequestParam MultipartFile imgFile) {
        productService.saveImage(imgFile);
        return new RedirectView("/admin_panel/add_product");
    }

}
