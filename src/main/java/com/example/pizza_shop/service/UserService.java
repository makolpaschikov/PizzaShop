package com.example.pizza_shop.service;

import com.example.pizza_shop.domain.user.User;
import com.example.pizza_shop.domain.user.UserRole;
import com.example.pizza_shop.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUsername(username);
    }

    /**
     * Adds a user to the database
     * @param user - added user
     * @param roles - set of the user roles
     * @return - true if the user was added into database, else false
     */
    public boolean addUser(User user, Set<UserRole> roles) {
        if (userDAO.findByUsername(user.getUsername()) != null) {
            return false;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(roles);
            userDAO.save(user);
            return true;
        }
    }

    /**
     * Returns a list of all users
     * @return - list of users
     */
    public List<User> getUsers() {
        return (List<User>) userDAO.findAll();
    }

    /**
     * Returns a list of users with role 'USER'
     * @return - list of users
     */
    public List<User> getOnlyUsers() {
        List<User> users = getUsers();
        users.removeIf(u -> u.getRoles().contains(UserRole.ADMIN));
        return users;
    }

    /**
     * Returns a list of users with role 'ADMIN'
     * @return - list of users
     */
    public List<User> getOnlyAdmins() {
        return userDAO.findByRoles(UserRole.ADMIN);
    }

    /**
     * Removes the user from database by his object
     * @param user - the user to be deleted
     */
    public void deleteUser(User user) {
        userDAO.deleteById(user.getUserID());
    }

    /**
     * Updates username
     * @param user - user
     * @param username - username of user
     * @return - true if the new username is correct, else false
     */
    public boolean updateUsername(User user, String username) {
        if (!user.getUsername().equals(username)) {
            if (!usernameIsAvailable(username)) {
                return false;
            } else {
                user.setUsername(username);
                userDAO.save(user);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Updates email
     * @param user - user
     * @param email - email of user
     * @return - true if the new email is correct, else false
     */
    public boolean updateEmail(User user, String email) {
        if (!user.getEmail().equals(email)) {
            if (!emailIsAvailable(email)) {
                return false;
            } else {
                user.setEmail(email);
                userDAO.save(user);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Updates password
     * @param user - user
     * @param oldPassword - old user password
     * @param newPassword - new user password
     * @param repeatedNewPassword - repeated user password (so that the new password is exactly correct)
     * @return - true if the new password is correct, else false
     */
    public boolean updatePassword(User user, String oldPassword, String newPassword, String repeatedNewPassword) {
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            if (!newPassword.equals(repeatedNewPassword)) {
                return false;
            } else {
                if (passwordEncoder.matches(newPassword, user.getPassword())) {
                    return false;
                } else {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userDAO.save(user);
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    private boolean usernameIsAvailable(String username) {
        return userDAO.findByUsername(username) == null;
    }

    private boolean emailIsAvailable(String email) {
        return userDAO.findByEmail(email) == null;
    }

}
