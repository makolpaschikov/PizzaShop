package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class IndexController {
    private final SessionService sessionService;

    @Autowired
    public IndexController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public Object getPage() {
        return new ModelAndView("index");
    }

    @PostMapping("/user_logout")
    public Object logout() {
        sessionService.closeSession(RequestContextHolder.currentRequestAttributes().getSessionId());
        return new RedirectView("/");
    }
}
