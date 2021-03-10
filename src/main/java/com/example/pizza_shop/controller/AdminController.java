package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin_panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserDAO userDAO;

    @Autowired
    public AdminController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public Object getPage() {
        return new ModelAndView("admin_panel");
    }

    @GetMapping("/users")
    public Object getUsersList(@AuthenticationPrincipal User user, Map<String, Object> model) {
        model.put("users_list", ((List<User>)userDAO.findAll()).remove(user));
        return new ModelAndView("users_list", model);
    }

}
