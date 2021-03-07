package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.Message;
import com.example.pizza_shop.repository.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    private final MessageDAO messageDAO;

    @Autowired
    public IndexController(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @GetMapping
    public Object getPage(Map<String,Object> model) {
        model.put("messages", messageDAO.findAll());
        return new ModelAndView("index", model);
    }

    @PostMapping("add_msg")
    public String addMsg(@RequestParam String msg) {
        messageDAO.save(new Message(msg));
        return "redirect:/";
    }

    @PostMapping("delete_msg")
    public String deleteMsg(@RequestParam Long id) {
        messageDAO.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("delete_all")
    public String deleteAll() {
        messageDAO.deleteAll();
        return "redirect:/";
    }
}

