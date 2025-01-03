package com.nick.ers.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nick.ers.exception.InvalidCredentialsException;
import com.nick.ers.model.User;
import com.nick.ers.model.dto.LoginDTO;
import com.nick.ers.model.dto.UserDTO;
import com.nick.ers.model.dto.UserRegisterationDTO;
import com.nick.ers.service.JwtService;
import com.nick.ers.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(" you have access now  ");
    }
    
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
    public ResponseEntity<String> userLogin(@RequestBody LoginDTO loginDTO, @RequestParam String role) {
        try {
            User user = userService.loginUser(loginDTO);
            if (!user.getRole().toString().equalsIgnoreCase(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Please use correct login portal");
            }
            return ResponseEntity.ok(jwtService.generateToken(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @Secured("MANAGER")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Secured("MANAGER")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
