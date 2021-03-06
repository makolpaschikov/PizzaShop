package com.example.pizza_shop.controller;

import com.example.pizza_shop.domain.user.User;
import com.example.pizza_shop.service.SessionService;
import com.example.pizza_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/account_update")
public class UpdateAccountController {
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public UpdateAccountController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping
    private Object getPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        formInitialization(user, model);
        return new ModelAndView("update_account", model);
    }

    @PostMapping("/update_username")
    private Object updateUsername(
            @AuthenticationPrincipal User user,
            @RequestParam String username,
            Map<String, Object> model
    ) {
        if (userService.updateUsername(user, username)) {
            sessionService.updateSession(user);
            return new RedirectView("/account_update");
        } else {
            model.put("username_error", "New username is incorrect");
            return new ModelAndView("update_account", formInitialization(user, model));
        }
    }

    @PostMapping("/update_email")
    private Object updateEmail(
            @AuthenticationPrincipal User user,
            @RequestParam String email,
            Map<String, Object> model
    ) {
        if(userService.updateEmail(user, email)){
            return new RedirectView("/account_update");
        } else {
            sessionService.updateSession(user);
            return new ModelAndView("update_account", formInitialization(user, model));
        }
    }

    @PostMapping("/update_password")
    private Object updatePassword(
            @AuthenticationPrincipal User user,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String repeatedNewPassword,
            Map<String, Object> model
    ) {
        if(userService.updatePassword(user, oldPassword, newPassword, repeatedNewPassword)){
            sessionService.updateSession(user);
            return new RedirectView("/account_update");
        } else {
            model.put("password_error", "The old password was entered incorrectly, or the new password is incorrect");
            return new ModelAndView("update_account", formInitialization(user, model));
        }
    }

    @PostMapping("/delete")
    public Object deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
        sessionService.closeSession(user);
        return new RedirectView("/");
    }

    private Map<String, Object> formInitialization(User user, Map<String, Object> model) {
        model.put("username", user.getUsername());
        model.put("email", user.getEmail());
        return model;
    }
}
