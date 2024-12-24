package com.nick.ers.controller;

import com.nick.ers.exception.RecordAlreadyExistsException;
import com.nick.ers.exception.UserNotFoundException;
import com.nick.ers.model.Reimbursement;
import com.nick.ers.service.JwtService;
import com.nick.ers.service.ReimbursementService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reimburse")
public class ReimburseController {

    @Autowired
    private ReimbursementService reimbursementService;

    @Autowired
    private JwtService jwttoken;

    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody Reimbursement reimburse, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            String username = jwttoken.extractUsername(token);
            Reimbursement createdReimbursement = reimbursementService.createReimbursement(reimburse, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReimbursement);
        } catch (RecordAlreadyExistsException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Secured("MANAGER")
    @PatchMapping("/{reimId}/approve")
    public ResponseEntity<String> approveReimburse(@PathVariable int reimId) {
        reimbursementService.approveReimbursement(reimId, Reimbursement.Status.APPROVED);
        return ResponseEntity.ok("Approved");
    }

    @Secured("MANAGER")
    @PatchMapping("/{reimId}/deny")
    public ResponseEntity<String> denyReimburse(@PathVariable int reimId) {
        reimbursementService.denyReimbursement(reimId, Reimbursement.Status.DENIED);
        return ResponseEntity.ok("Denied");
    }
}
