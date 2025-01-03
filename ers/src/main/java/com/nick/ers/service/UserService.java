package com.nick.ers.service;

import com.nick.ers.exception.InvalidCredentialsException;
import com.nick.ers.exception.ResourceNotFoundException;
import com.nick.ers.exception.UserConflictException;
import com.nick.ers.exception.UserNotFoundException;
import com.nick.ers.model.User;
import com.nick.ers.model.User.Role;
import com.nick.ers.model.dto.LoginDTO;
import com.nick.ers.model.dto.UserDTO;
import com.nick.ers.model.dto.UserRegisterationDTO;
import com.nick.ers.repository.ReimbursementRepository;
import com.nick.ers.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;




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

    @Autowired
    private ReimbursementRepository reimbursementRepository;

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

    @Secured("MANAGER")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll()
                                        .stream()
                                        .filter(user -> !user.getRole().name().equalsIgnoreCase("MANAGER"))
                                        .collect(Collectors.toList());
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            if (user.getReimbursement() != null) {
                user.getReimbursement().forEach(reimbursement -> {
                    reimbursement.setUser(null);
                });
            }
        userRepository.delete(user);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setRole(user.getRole().name());
        return dto;
    }
}
