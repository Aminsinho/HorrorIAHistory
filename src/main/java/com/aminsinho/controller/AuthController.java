package com.aminsinho.controller;

import com.aminsinho.models.User;
import com.aminsinho.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private final String SECRET_KEY = "secret";

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/token")
    public Map<String, String> generateToken(@RequestBody User user) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticatedUser = userService.findByUsername(userDetails.getUsername());

        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId", authenticatedUser.getId().toString()) // Incluir userId en el token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}