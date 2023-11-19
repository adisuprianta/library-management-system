package com.enigma.library_management_system.service.impl;

import com.enigma.library_management_system.dto.request.NewLoanRequest;
import com.enigma.library_management_system.dto.response.DetailLoanResponse;
import com.enigma.library_management_system.dto.response.LoanResponse;
import com.enigma.library_management_system.entity.BookCopies;
import com.enigma.library_management_system.entity.DetailLoan;
import com.enigma.library_management_system.entity.Loan;
import com.enigma.library_management_system.entity.Member;
import com.enigma.library_management_system.repository.LoanRepository;
import com.enigma.library_management_system.service.BookCopiesService;
import com.enigma.library_management_system.service.LoanService;
import com.enigma.library_management_system.service.MemberService;
import com.enigma.library_management_system.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final MemberService memberService;
    private final BookCopiesService bookCopiesService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoanResponse createLoan(NewLoanRequest request) {
        try {
            validationUtil.validate(request);
            Member member = memberService.findById(request.getMemberId());
            if (!member.getStatus()){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"member cannot borrow");
            }

            List<DetailLoan> detailLoans = request.getDetailLoans().stream()
                    .map(detailLoanRequest -> {
                        BookCopies bookCopies = bookCopiesService.findById(detailLoanRequest.getBookCopiesId());
                        if (!bookCopies.getAvailabilityStatus()){
                            throw new ResponseStatusException(HttpStatus.CONFLICT,"Book is not available for borrowing");
                        }
                        bookCopies.setAvailabilityStatus(false);
                        return DetailLoan.builder()
                                .bookCopies(bookCopies)
                                .build();
                    }).collect(Collectors.toList());
            Loan loan = Loan.builder()
                    .dueDate(request.getDueDate())
                    .checkinDate(LocalDate.now())
                    .member(member)
                    .detailLoans(detailLoans)
                    .build();
            loanRepository.saveAndFlush(loan);
            detailLoans.forEach(
                    detailLoan -> detailLoan.setLoan(loan)
            );
            return mapToResponse(loan);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "cannot create transaction");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoanResponse updateLoan(String id) {
        Loan loan = getLoan(id);
        loan.setCheckoutDate(LocalDate.now());
        loan.getDetailLoans().forEach(detailLoan ->
                detailLoan.getBookCopies().setAvailabilityStatus(true)
        );
        loanRepository.saveAndFlush(loan);
        return mapToResponse(loan);
    }

    @Transactional(readOnly = true)
    @Override
    public LoanResponse finById(String id) {
        Loan loan = getLoan(id);
        return mapToResponse(loan);
    }


    @Transactional(readOnly = true)
    @Override
    public List<LoanResponse> getAll() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private LoanResponse mapToResponse(Loan loan) {
        List<DetailLoanResponse> detailLoanResponses = loan.getDetailLoans().stream().map(detailLoan ->
                DetailLoanResponse.builder()
                        .detailLoanId(detailLoan.getId())
                        .isbn(detailLoan.getBookCopies().getIsbn())
                        .availabilityStatus(detailLoan.getBookCopies().getAvailabilityStatus())
                        .publishedDate(detailLoan.getBookCopies().getBook().getPublishedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .title(detailLoan.getBookCopies().getBook().getTitle())
                        .author(detailLoan.getBookCopies().getBook().getAuthor())
                        .build()
        ).collect(Collectors.toList());
        String chckoutDate=null;
        if (loan.getCheckoutDate()!=null){
            chckoutDate=loan.getCheckoutDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        return LoanResponse.builder()
                .checkinDate(loan.getCheckinDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .checkoutDate(chckoutDate)
                .dueDate(loan.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .memberName(loan.getMember().getName())
                .detailLoans(detailLoanResponses)
                .id(loan.getId())
                .build();
    }

    private Loan getLoan(String id) {
        return loanRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "there are no loans")
        );
    }
}
