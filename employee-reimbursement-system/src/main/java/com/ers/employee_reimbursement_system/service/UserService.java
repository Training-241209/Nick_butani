package com.ers.employee_reimbursement_system.service;

import com.ers.employee_reimbursement_system.entity.*;
import com.ers.employee_reimbursement_system.entity.dto.*;
import com.ers.employee_reimbursement_system.DAOs.*;
import com.ers.employee_reimbursement_system.entity.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(RegisterDTO registerUserDTO) throws IllegalArgumentException {
        Optional<User> user = userDAO.findByUsername(registerUserDTO.getUsername());
        if(user.isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }

        if (registerUserDTO.getUsername() == null || registerUserDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (registerUserDTO.getPassword() == null || registerUserDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        String role = registerUserDTO.getRole() != null ? registerUserDTO.getRole() : "employee";

        User newUser = new User();
        newUser.setUsername(registerUserDTO.getUsername());
        newUser.setPassword(registerUserDTO.getPassword()); 
        newUser.setFirstName(registerUserDTO.getFirstName()); 
        newUser.setLastName(registerUserDTO.getLastName());
        newUser.setRole(role);
        newUser.setEmail(registerUserDTO.getEmail()); 
        return userDAO.save(newUser);
    }

    public Optional<User> loginUser(LoginDTO loginUserDTO) throws IllegalArgumentException {
        if (loginUserDTO.getUsername() == null || loginUserDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (loginUserDTO.getPassword() == null || loginUserDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return userDAO.findByUsernameAndPassword(loginUserDTO.getUsername(), loginUserDTO.getPassword());
    }

    public void deleteUser(int userId) {
        Optional<User> user = userDAO.findById(userId);
        if(user.isPresent()){
            userDAO.delete(user.get());
        } else {
            throw new IllegalArgumentException("User with id " + userId + " does not exist");
        }
    }
}