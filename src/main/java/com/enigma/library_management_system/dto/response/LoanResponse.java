package com.enigma.library_management_system.dto.response;

import com.enigma.library_management_system.entity.Member;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResponse {
    private String id;
    private String checkoutDate;
    private String dueDate;
    private String checkinDate;
    private String memberName;
    private List<DetailLoanResponse> detailLoans;
}
