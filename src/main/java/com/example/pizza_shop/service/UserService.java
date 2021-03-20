package com.example.pizza_shop.service;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import com.example.pizza_shop.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUsername(username);
    }

    //======================
    // ADD
    //======================
    public String addUser(User user, Set<UserRole> roles) {
        if (userDAO.findByUsername(user.getUsername()) != null) {
            return "User with this username is already registered";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(roles);
            userDAO.save(user);
            return null;
        }
    }

    //======================
    // GET
    //======================
    public List<User> getUsers() {
        return (List<User>) userDAO.findAll();
    }

    public List<User> getOnlyUsers() {
        List<User> users = getUsers();
        users.removeIf(user -> user.getRoles().contains(UserRole.ADMIN));
        return users;
    }

    public List<User> getOnlyAdmins() {
        return userDAO.findByRoles(UserRole.ADMIN);
    }

    public User getByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    //======================
    // DELETE
    //======================
    public void deleteUser(User user) {
        userDAO.deleteById(user.getUserID());
    }

    //======================
    // UPDATE
    //======================
    public String updateUsername(User user, String username) {
        if (!user.getUsername().equals(username)) {
            if (!usernameIsAvailable(username)) {
                return "This username is taken!";
            } else {
                user.setUsername(username);
                userDAO.save(user);
                return null;
            }
        } else {
            return "The usernames are the same!";
        }
    }

    public String updateEmail(User user, String email) {
        if (!user.getEmail().equals(email)) {
            if (!emailIsAvailable(email)) {
                return "This email is taken!";
            } else {
                user.setEmail(email);
                userDAO.save(user);
                return null;
            }
        } else {
            return "The emails are the same!";
        }
    }

    public String updatePassword(User user, String oldPassword, String newPassword, String repeatedNewPassword) {
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            if (!newPassword.equals(repeatedNewPassword)) {
                return "Password mismatch!";
            } else {
                if (passwordEncoder.matches(newPassword, user.getPassword())) {
                    return "The old and new passwords are the same!";
                } else {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userDAO.save(user);
                    return null;
                }
            }
        } else {
            return "The previous password was entered incorrectly!";
        }
    }

    //======================
    // AVAILABLE
    //======================
    public boolean usernameIsAvailable(String username) {
        return userDAO.findByUsername(username) == null;
    }

    public boolean emailIsAvailable(String email) {
        return userDAO.findByEmail(email) == null;
    }

}
