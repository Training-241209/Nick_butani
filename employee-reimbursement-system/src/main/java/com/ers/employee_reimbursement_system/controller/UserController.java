package com.ers.employee_reimbursement_system.controller;

import com.ers.employee_reimbursement_system.entity.dto.*;
import com.ers.employee_reimbursement_system.entity.*;
import com.ers.employee_reimbursement_system.service.*;
import com.ers.employee_reimbursement_system.util.JwtTokenUtil;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = { "http://localhost:8080" }, allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerUserDTO){
        System.out.println("Attempting to register user: " + registerUserDTO.getUsername());
        try {
            userService.registerUser(registerUserDTO);
            User user = new User();
            user.setUsername(registerUserDTO.getUsername());
            user.setEmail(registerUserDTO.getEmail());
            user.setFirstName(registerUserDTO.getFirstName());
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.status(201).body("User " + registerUserDTO.getUsername() + " was created successfully!" + token);
        } catch (IllegalArgumentException e){
            System.out.println("Registration failed: " + e.getMessage());
            return ResponseEntity.status(400).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginUserDTO, HttpSession session){
        System.out.println("Attempting to login with username: " + loginUserDTO.getUsername());
        Optional<User> optionalUser = userService.loginUser(loginUserDTO);

        if (optionalUser.isEmpty()) {
            System.out.println("Login failed: No user found with provided credentials");
            return ResponseEntity.status(401).body("Login Failed!");
        }

        User user = optionalUser.get();
        System.out.println("Login successful for user: " + user.getUsername());

        session.setAttribute("userId", user.getUserId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());

        System.out.println("Session attributes set: userId=" + user.getUserId() + ", username=" + user.getUsername() + ", role=" + user.getRole());

        return ResponseEntity.ok(new LoginResponseDTO(user.getUserId(), user.getUsername(), user.getRole()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User " + userId + " was deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete user: " + e.getMessage());
        }
    }
}