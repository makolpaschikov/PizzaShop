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
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class MessageController {
    private final MessageDAO messageDAO;

    @Autowired
    public MessageController(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @GetMapping
    public Object getPage(Map<String,Object> model) {
        model.put("messages", messageDAO.findAll());
        return new ModelAndView("message", model);
    }

    @PostMapping("add_msg")
    public RedirectView addMsg(@RequestParam String msg) {
        messageDAO.save(new Message(msg));
        return new RedirectView("/");
    }

    @PostMapping("delete_msg")
    public RedirectView deleteMsg(@RequestParam Long id) {
        messageDAO.deleteById(id);
        return new RedirectView("/");
    }

    @PostMapping("delete_all")
    public RedirectView deleteAll() {
        messageDAO.deleteAll();
        return new RedirectView("/");
    }
}

