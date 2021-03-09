package com.example.pizza_shop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin_panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @GetMapping
    public Object getPage() {
        return new ModelAndView("admin_panel");
    }
}
