package com.example.pizza_shop.service;

import com.example.pizza_shop.domain.User;
import com.example.pizza_shop.domain.UserRole;
import com.example.pizza_shop.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void addUser(User user) {
        userDAO.save(user);
    }

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

    public void deleteUsers() {
        userDAO.deleteAll();
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public void deleteUser(Long id) {
        userDAO.deleteById(id);
    }

}
