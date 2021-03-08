package com.example.pizza_shop.controller.package4test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("shop")
public class ShopController {
    @GetMapping
    public Object getPage(Map<String,Object> model) {
        return new ModelAndView("shop", model);
    }
}
