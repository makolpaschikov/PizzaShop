package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import com.example.pizza_shop.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("signup")
public class SignupController {
    private final UserDAO userDAO;

    @Autowired
    public SignupController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public Object getPage() {
        return new ModelAndView("signup");
    }

    @PostMapping
    public Object signup(User user, Map<String, Object> model) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            model.put("error_msg", "User with this email is already registered");
            return new ModelAndView("signup", model);
        } else {
            user.setActive(true);
            user.setRoles(Collections.singleton(UserRole.USER));
            userDAO.save(user);
            return new RedirectView("/login");
        }
    }
}
