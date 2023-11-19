package com.enigma.library_management_system.dto.response;


import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailLoanResponse {
    private String detailLoanId;
    private List<BookCopiesResponse> bookCopies;
}
