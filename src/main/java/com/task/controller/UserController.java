package com.task.controller;

import com.task.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        System.out.println("tarek===>start");
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        System.out.println("tarek===>1");
        /*
        String token = jwtUtil.generateToken(request.getUsername());
        System.out.println("tarek===>end");
        return ResponseEntity.ok(new AuthResponse(token));
         */
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("user signed success", HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("tarek===>hello");
        return "Hello, you are authenticated!";
    }
}


