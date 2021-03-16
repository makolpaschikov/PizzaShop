package com.example.pizza_shop.service;

import com.example.pizza_shop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Service
public class SessionService {
    private final SessionRegistry sessionRegistry;

    @Autowired
    public SessionService(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public void updateSession(User user) {
        Authentication newAuth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        RequestContextHolder.currentRequestAttributes().setAttribute("SPRING_SECURITY_CONTEXT", newAuth, RequestAttributes.SCOPE_SESSION);
    }

    public void closeSession(String sessionID) {
        sessionRegistry.getSessionInformation(sessionID).expireNow();
    }

    public void closeSession(User user) {
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(user, true);
        for (SessionInformation session : sessions) {
            session.expireNow();
        }
    }
}
