package com.enigma.library_management_system.repository;

import com.enigma.library_management_system.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan,String> {
}
