package com.ers.employee_reimbursement_system.DAOs;

import com.ers.employee_reimbursement_system.entity.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {
    List<Reimbursement> findByUserUserId(int userId);
}