package com.example.pizza_shop.controller;

import com.example.pizza_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/admin_panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Object getPage() {
        return new ModelAndView("admin_panel");
    }

    @GetMapping("/users")
    public Object getUsers(Map<String, Object> model) {
        model.put("users_list", userService.getOnlyUsers());
        return new ModelAndView("user_list", model);
    }

    @GetMapping("/admins")
    public Object getAdmins(Map<String, Object> model) {
        model.put("users_list", userService.getOnlyAdmins());
        return new ModelAndView("user_list", model);
    }

}
