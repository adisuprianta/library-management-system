package com.enigma.library_management_system.dto.request;

import com.enigma.library_management_system.dto.response.DetailLoanResponse;
import com.enigma.library_management_system.dto.response.MemberResponse;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {
    @NotBlank(message = "due date is required")
    private LocalDate dueDate;
    @NotBlank(message = "member is required")
    private String memberId;
    @NotNull(message = "detail loan is required")
    private List<DetailLoanRequest> detailLoans;
}
