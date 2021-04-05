package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import com.example.pizza_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Object getPage() {
        return new ModelAndView("signup");
    }

    @PostMapping
    public Object signup(User user, Map<String, Object> model) {
        if (userService.addUser(user, Collections.singleton(UserRole.USER))) {
            return new RedirectView("/login");
        } else {
            model.put("error_msg", "User with this username is already registered");
            return new ModelAndView("signup", model);
        }
    }

    private void createAdmin() {
        Set<UserRole> r = new HashSet<>();
        r.add(UserRole.ADMIN);
        r.add(UserRole.USER);
        User u = new User();
        u.setUsername("admin");
        u.setPassword("nocheese");
        u.setEmail("busines-nocheesepizza@gmail.com");
        userService.addUser(u, r);
    }
}
