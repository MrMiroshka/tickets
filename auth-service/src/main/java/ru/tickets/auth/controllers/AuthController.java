package ru.tickets.auth.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.tickets.api.auth.AuthRequest;
import ru.tickets.api.auth.AuthResponse;
import ru.tickets.auth.services.JwtService;


@RestController
@Slf4j

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest user){
        log.info("User name: {},user password {}", user.getUsername(), user.getPassword());

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())); // проверка на валидность данных


            String jwtToken = jwtService.generateJwtToken((UserDetails) authenticate.getPrincipal());
            return new AuthResponse(jwtToken);
        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }
}
