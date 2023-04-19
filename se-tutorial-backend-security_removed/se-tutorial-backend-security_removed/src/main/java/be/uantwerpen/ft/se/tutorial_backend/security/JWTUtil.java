package be.uantwerpen.ft.se.tutorial_backend.security;

import be.uantwerpen.ft.se.tutorial_backend.model.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import be.uantwerpen.ft.se.tutorial_backend.model.users.Role;

import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(User user) throws IllegalArgumentException, JWTCreationException {
        List<String> roles = user.getRoles().stream()
                .map(Role::getName).toList();
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", user.getEmail())
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // expires in 24 hours
                .withIssuer("UA_FTI_SE_TUTORIAL")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("UA_FTI_SE_TUTORIAL")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}
//This code is for creating and checking a secret code that only certain people can use.
// It's like having a special password that only certain people know so they can get into a special club.
//
//The first part of the code creates the secret code using information about the person trying to use it.
// It includes things like their email address and what kind of role they have. It also sets an expiration
// time for how long the secret code will work.
//
//The second part of the code checks to make sure the secret code is valid and hasn't expired.
// If the secret code is valid, it will give back the email address of the person who originally created the code.
