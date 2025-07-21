package com.task.controller;


import com.task.dto.AuthResponseDTO;
import com.task.dto.LoginDto;
import com.task.dto.RegisterDto;
import com.task.models.UserEntity;
import com.task.respositry.UserRepository;
import com.task.service.CustomUserDetailsService;
import com.task.security.JWTGenerator;
import exception.EntityAlreadyExist;
import exception.EntityBadRequest;
import exception.EntityErorrResponse;
import exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,
                          CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
        if (loginDto.getUsername() == null || loginDto.getUsername().equals("")) {
            throw new EntityBadRequest(" Please Enter Username");
        } else if (loginDto.getPassword() == null || loginDto.getUsername().equals("")) {
            throw new EntityBadRequest(" Please Enter Password");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //String token = jwtGenerator.generateToken(authentication);
        System.out.println("Password input====> " + loginDto.getPassword());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
        String token = jwtGenerator.generateToken(userDetails);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {

        if (registerDto.getUsername() == null || registerDto.getUsername().equals("")) {
            throw new EntityBadRequest(" Please Enter Username");
        } else if (registerDto.getPassword() == null || registerDto.getUsername().equals("")) {
            throw new EntityBadRequest(" Please Enter Password");
        } else if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new EntityAlreadyExist(" Username already Registered!");
        }
        /*
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username already Registered!", HttpStatus.BAD_REQUEST);
        }
        */


        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        userRepository.save(user);

        //return new ResponseEntity<>("User registered success!", HttpStatus.OK);
        // Response body
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");

        // Return HTTP 200 OK with JSON response
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler
    public ResponseEntity<EntityErorrResponse> handleEntityNotFoundException(EntityNotFoundException exc) {
        EntityErorrResponse error = new EntityErorrResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<EntityErorrResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EntityErorrResponse> handleEntityAlreadyExistException(EntityAlreadyExist exc) {
        EntityErorrResponse error = new EntityErorrResponse();
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<EntityErorrResponse>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<EntityErorrResponse> handleEntityBadRequestException(EntityBadRequest exc) {
        EntityErorrResponse error = new EntityErorrResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<EntityErorrResponse>(error, HttpStatus.BAD_REQUEST);

    }


}



