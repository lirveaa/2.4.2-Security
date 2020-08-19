package org.javamentor.spring.security;

import org.javamentor.spring.dao.DaoUser;
import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        System.out.println("We are in AuthProviderImpl authentication.getName() : ");
        System.out.println("Session authented user is " + login);

        User user = userService.getUser(login);

        if (user == null) {
            System.out.println("User not found =====");
            throw new UsernameNotFoundException("User not found");
        }
        String password = authentication.getCredentials().toString();
        if (!password.equals(user.getPassword())) {
            System.out.println("wrong password (Bad credentials)");
            throw new BadCredentialsException("Bad credentials!");
        }

        System.out.println("Was found user " + user + " by this login " + login);
        System.out.println("authentication.getDetails() = " + authentication.getDetails());
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        System.out.println("authentication.getCredentials().toStr = " + authentication.getCredentials().toString());

        Set<Role> roles = userService.getUser(login).getRoles();
        System.out.println("His roles: " + roles);

        Set<GrantedAuthority> authorities = new HashSet();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new UsernamePasswordAuthenticationToken(login, user.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
