package com.example.pizza_shop.service;

import com.example.pizza_shop.domain.Basket;
import com.example.pizza_shop.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String pizzeriaMail;
    private final JavaMailSender MAIL_SENDER;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.MAIL_SENDER = mailSender;
    }

    /**
     * Collects a message for the user and sends him a link to activate the account by email
     * @param user - recipient user
     */
    public void sendOrder(User user, Basket basket, String address) {
        StringBuilder message = new StringBuilder();
        message.append("Заказ №").append(basket.getBasketID()).append(address).append("\n\n");
        for (String product : basket.getProducts()) message.append(product).append("\n");
        send(user.getEmail(), "Заказ №" + basket.getBasketID(), message.toString());
    }

    // Sends a letter
    private void send(String emailTo, String subject, String message) {
        // send to user
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(pizzeriaMail);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\nСпасибо за Ваш заказ!");
        MAIL_SENDER.send(mailMessage);

        // send to pizzeria
        mailMessage.setFrom(pizzeriaMail);
        mailMessage.setTo(pizzeriaMail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        MAIL_SENDER.send(mailMessage);
    }
}
