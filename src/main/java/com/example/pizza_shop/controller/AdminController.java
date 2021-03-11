package com.example.pizza_shop.controller;

import com.example.pizza_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
        model.put("users_list", userService.getUsers());
        return new ModelAndView("user_list", model);
    }

    @PostMapping("/users/delete_user")
    public Object deleteUser(@RequestParam Long userID) {
        userService.deleteUser(userID);
        return new RedirectView("/admin_panel/users");
    }

}
