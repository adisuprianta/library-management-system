package com.enigma.library_management_system.service;

import com.enigma.library_management_system.dto.request.NewLoanRequest;
import com.enigma.library_management_system.dto.response.LoanResponse;

import java.util.List;

public interface LoanService {
    LoanResponse createLoan(NewLoanRequest request);
    LoanResponse updateLoan(String id);
    LoanResponse finById(String id);
    List<LoanResponse> getAll();
}
