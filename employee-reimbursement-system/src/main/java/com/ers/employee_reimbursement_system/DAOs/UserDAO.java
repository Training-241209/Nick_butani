package com.ers.employee_reimbursement_system.DAOs;

import com.ers.employee_reimbursement_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String username);
    public Optional<User> findByUsernameAndPassword(String username, String password);

}