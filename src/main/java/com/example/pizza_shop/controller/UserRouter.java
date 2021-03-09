package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/router")
public class UserRouter {
    @GetMapping
    public Object routing(@AuthenticationPrincipal User user) {
        if (user.getAuthorities().contains(UserRole.ADMIN)) {
            return new RedirectView("/admin_panel");
        } else {
            return new RedirectView("/shop");
        }
    }
}
