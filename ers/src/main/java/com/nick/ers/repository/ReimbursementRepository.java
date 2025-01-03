package com.nick.ers.repository;

import com.nick.ers.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer>{
    @Modifying
    @Query("DELETE FROM Reimbursement r WHERE r.user.userId = :userId")
    void deleteByUserId(@Param("userId") int userId);
    List<Reimbursement> findByUserUserId(int userId);
}
