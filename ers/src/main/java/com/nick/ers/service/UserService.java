package com.nick.ers.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nick.ers.exception.InvalidCredentialsException;
import com.nick.ers.exception.UserConflictException;
import com.nick.ers.exception.UserNotFoundException;
import com.nick.ers.model.User;
import com.nick.ers.model.User.Role;
import com.nick.ers.model.dto.LoginDTO;
import com.nick.ers.model.dto.UserRegisterationDTO;
import com.nick.ers.repository.UserRepository;


@Configuration
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // @Bean
    // PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserRegisterationDTO userRegisterationDTO) throws Exception{
        Optional<User> user = userRepository.findByUsername(userRegisterationDTO.getUsername());
        if(user.isPresent()){
            throw new UserConflictException("User with username '" + userRegisterationDTO.getUsername() + "' already exists.");
        }
        else{
            User createUser = new User();
            createUser.setFirstname(userRegisterationDTO.getFirstname());
            createUser.setLastname(userRegisterationDTO.getLastname());
            createUser.setUsername(userRegisterationDTO.getUsername());
            createUser.setPassword(passwordEncoder.encode(userRegisterationDTO.getPassword()));  
            createUser.setRole(userRegisterationDTO.getRole() != null ? userRegisterationDTO.getRole() : Role.EMPLOYEE);
            return userRepository.save(createUser);
        }
    }

    public User loginUser(LoginDTO loginDTO){
        User user = userRepository.findByUsername(loginDTO.getUsername())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));    
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found."));
    }

}
