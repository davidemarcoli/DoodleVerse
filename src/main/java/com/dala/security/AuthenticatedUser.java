package com.dala.security;

import com.dala.data.user.User;
import com.dala.data.user.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticatedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get the Authentication Object from the current logged in User
     * @return the Authentication Object
     */
    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    /**
     * Get the User by the username of the current logged in user
     * @return the User
     */
    public Optional<User> get() {
        return getAuthentication().map(authentication -> userRepository.findByUsername(authentication.getName()));
    }

    /**
     * Log the user out
     */
    public void logout() {
        UI.getCurrent().getPage().setLocation(SecurityConfiguration.LOGOUT_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

}
