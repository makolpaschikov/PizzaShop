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
import java.util.Map;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Object getPage() {
/*        Set<UserRole> r = new HashSet<>();
        r.add(UserRole.ADMIN);
        r.add(UserRole.USER);
        User u = new User();
        u.setUsername("admin");
        u.setPassword(passwordEncoder.encode("nocheese"));
        u.setEmail("busines-nocheesepizza@gmail.com");
        u.setActive(true);
        u.setRoles(r);
        userService.addUser(u);*/
        return new ModelAndView("signup");
    }

    @PostMapping
    public Object signup(User user, Map<String, Object> model) {
        if (userService.getByUsername(user.getUsername()) != null) {
            model.put("error_msg", "User with this username is already registered");
            return new ModelAndView("signup", model);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(Collections.singleton(UserRole.USER));
            userService.addUser(user);
            return new RedirectView("/login");
        }
    }
}
