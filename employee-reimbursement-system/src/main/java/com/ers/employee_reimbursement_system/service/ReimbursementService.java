package com.ers.employee_reimbursement_system.service;


import com.ers.employee_reimbursement_system.DAOs.ReimbursementDAO;
import com.ers.employee_reimbursement_system.entity.Reimbursement;
import com.ers.employee_reimbursement_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    @Autowired
    private ReimbursementDAO reimbursementDAO;

    public List<Reimbursement> findAll() {
        return reimbursementDAO.findAll();
    }

    @Transactional
    public Reimbursement createReimbursement(Reimbursement request, Integer userId) {
        try {
            User user = new User();
            user.setUserId(userId);  // No need to parse, userId is already an Integer
            request.setDateSubmitted(LocalDate.now());
            request.setStatus(Reimbursement.ReimbursementStatus.PENDING);
            request.setUser(user);
            return reimbursementDAO.save(request);
        } catch (Exception e) {
            System.out.println("Error creating reimbursement: " + e.getMessage());
            throw e; // Rethrow the exception
        }
    }

    public Reimbursement approveReimbursement(int id) {
        Optional<Reimbursement> found = reimbursementDAO.findById(id);
        if (found.isPresent()) {
            Reimbursement request = found.get();
            request.setStatus(Reimbursement.ReimbursementStatus.APPROVED);
            return reimbursementDAO.save(request);
        } else {
            throw new RuntimeException("Reimbursement request not found");
        }
    }

    public Reimbursement denyReimbursement(int id) {
        Optional<Reimbursement> found = reimbursementDAO.findById(id);
        if (found.isPresent()) {
            Reimbursement request = found.get();
            request.setStatus(Reimbursement.ReimbursementStatus.DENIED);
            return reimbursementDAO.save(request);
        } else {
            throw new RuntimeException("Reimbursement request not found");
        }
    }

    public List<Reimbursement> findByUserId(int userId) {
        return reimbursementDAO.findByUserUserId(userId);
    }
}