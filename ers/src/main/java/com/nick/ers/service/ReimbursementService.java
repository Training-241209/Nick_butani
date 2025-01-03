package com.nick.ers.service;

import com.nick.ers.exception.RecordAlreadyExistsException;
import com.nick.ers.model.Reimbursement;
import com.nick.ers.model.User;
import com.nick.ers.model.dto.ReimburseDTO;
import com.nick.ers.repository.ReimbursementRepository;
import com.nick.ers.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Configuration
@Service
public class ReimbursementService {

    @Autowired
    private ReimbursementRepository reimbursementRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;



       public List<Reimbursement> getReimbursementsByUsername(String username) {
        try {
            System.out.println("Attempting to fetch user with username: " + username);
            User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));     
            System.out.println("Found user with ID: " + user.getUserId());
            List<Reimbursement> reimbursements = reimbursementRepository.findByUserUserId(user.getUserId());
            System.out.println("Found " + reimbursements.size() + " reimbursements");
            return reimbursements;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getReimbursementsByUsername: " + e.getMessage());
            throw e;
        }
    }

    public List<ReimburseDTO> getAllEmployReimburse() {
        List<Reimbursement> reimbursements = reimbursementRepository.findAll();
        return reimbursements.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ReimburseDTO toDto(Reimbursement reimbursement) {
        ReimburseDTO dto = new ReimburseDTO();
        dto.setReimId(reimbursement.getReimId());
        dto.setDescription(reimbursement.getDescription());
        dto.setAmount(reimbursement.getAmount());
        dto.setStatus(reimbursement.getStatus().name());
        if (reimbursement.getUser() != null) {
            dto.setEmployeeName(reimbursement.getUser().getName());
        }
        return dto;
    }

    public Reimbursement createReimbursement(Reimbursement reimburse, String username) {
        reimburse.setStatus(Reimbursement.Status.PENDING);
        User user = userService.findByUsername(username);
        if (reimbursementRepository.existsById(reimburse.getReimId())) {
            throw new RecordAlreadyExistsException("Reimbursement with ID '" + reimburse.getReimId() + "' already exists.");
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
