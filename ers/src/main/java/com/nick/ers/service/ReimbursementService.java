package com.nick.ers.service;

import java.io.ObjectInputFilter.Status;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.nick.ers.exception.RecordAlreadyExistsException;
import com.nick.ers.model.Reimbursement;
import com.nick.ers.model.User;
import com.nick.ers.model.dto.ReimburseDTO;
import com.nick.ers.repository.ReimbursementRepository;
import com.nick.ers.repository.UserRepository;

@Configuration
@Service
public class ReimbursementService {

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    public Reimbursement createReimbursement(Reimbursement reimburse, String username) {
        reimburse.setStatus(Reimbursement.Status.PENDING);
        User user = userService.findByUsername(username);
        if (reimbursementRepository.existsById(reimburse.getreimId())) {
            throw new RecordAlreadyExistsException("Reimbursement with ID '" + reimburse.getreimId() + "' already exists.");
        }   
        reimburse.setUser(user);
        return reimbursementRepository.save(reimburse);
    }

    public Reimbursement approveReimbursement(int reimId, Reimbursement.Status status) {
        Optional<Reimbursement> reimburse = reimbursementRepository.findById(reimId);
        if (reimburse.isPresent()) {
            Reimbursement reimbursement = reimburse.get();
            reimbursement.setStatus(status.APPROVED);
            return reimbursementRepository.save(reimbursement);
        } else {
            throw new RecordAlreadyExistsException("Reimbursement with ID '" + reimId + "' not found.");
        }
        
    }

    public Reimbursement denyReimbursement(int reimId, Reimbursement.Status status) {
        Optional<Reimbursement> reimburse = reimbursementRepository.findById(reimId);
        if (reimburse.isPresent()) {
            Reimbursement reimbursement = reimburse.get();
            reimbursement.setStatus(status.DENIED);
            return reimbursementRepository.save(reimbursement);
        } 
        else {
            throw new RecordAlreadyExistsException("Reimbursement with ID '" + reimId + "' not found.");
        }
    }
}
