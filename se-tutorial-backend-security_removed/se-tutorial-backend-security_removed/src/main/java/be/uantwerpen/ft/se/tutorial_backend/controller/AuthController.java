package be.uantwerpen.ft.se.tutorial_backend.controller;

import be.uantwerpen.ft.se.tutorial_backend.model.users.User;
import be.uantwerpen.ft.se.tutorial_backend.model.users.dto.LoginCredentials;
import be.uantwerpen.ft.se.tutorial_backend.repository.UserRepository;
import be.uantwerpen.ft.se.tutorial_backend.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;

    Logger logger = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        logger.info("POST: /login");
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);
            User user = userRepository.findByEmail(body.getEmail()).get();
            String token = jwtUtil.generateToken(user);

            return Collections.singletonMap("jwt-token", token);
        } catch (NoSuchElementException e){
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

}

//The @PostMapping("/login") annotation specifies that this endpoint will only a
// ccept HTTP POST requests with "/login" appended to the "/auth" endpoint.
//
//The loginHandler method handles the user login process. It takes in a
// LoginCredentials object as the request body, which contains the user's email and password.
// It authenticates the user's credentials using the AuthenticationManager, generates a JSON Web Token using
// the JWTUtil class, and returns the token as a map. If the login credentials are invalid,
// it throws a RuntimeException.
//
//The Logger object is used to log information about the login request.