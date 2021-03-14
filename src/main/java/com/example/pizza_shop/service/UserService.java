package com.example.pizza_shop.service;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import com.example.pizza_shop.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUsername(username);
    }

    //======================
    // ADD
    //======================
    public void addUser(User user) {
        userDAO.save(user);
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
    public void deleteUsers() {
        userDAO.deleteAll();
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public void deleteUser(Long id) {
        userDAO.deleteById(id);
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
