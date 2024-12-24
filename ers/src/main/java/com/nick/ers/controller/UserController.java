package com.nick.ers.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nick.ers.exception.AuthenticationException;
import com.nick.ers.exception.InvalidCredentialsException;
import com.nick.ers.model.User;
import com.nick.ers.model.dto.LoginDTO;
import com.nick.ers.model.dto.UserRegisterationDTO;
import com.nick.ers.repository.UserRepository;
import com.nick.ers.service.JwtService;
import com.nick.ers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // @Autowired 
    // private UserRepository userRepository;
    
    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserRegisterationDTO userRegisterationDTO) throws Exception {
        User user = userService.createUser(userRegisterationDTO);
        // user.setPassword(null);
        System.out.println(user);
        return ResponseEntity.ok(user);
    }

    // @PostMapping("/login")
    // public ResponseEntity<String> userLogin(@RequestBody LoginDTO loginDTO) throws Exception {
    //     Optional<User> optionalUser = userService.loginUser(loginDTO);

    //     if (optionalUser.isEmpty()) {
    //         System.out.println("Login failed: No user found with provided credentials");
    //         return ResponseEntity.status(401).body("Login Failed!");
    //     }
    //     return ResponseEntity.ok("success");
    // }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginDTO loginDTO) throws Exception{
        try {
            User authenticatedUser = userService.loginUser(loginDTO);
            String token = jwtService.generateToken(authenticatedUser);
            return ResponseEntity.ok(token);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred during login");
        }

    }

    
}
