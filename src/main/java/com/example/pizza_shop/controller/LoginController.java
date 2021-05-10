package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public Object getPage(@AuthenticationPrincipal User user) {
        if (user == null) return new ModelAndView("login");
        else return new RedirectView("/router");
    }
}
