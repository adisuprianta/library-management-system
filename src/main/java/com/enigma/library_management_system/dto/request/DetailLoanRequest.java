package com.enigma.library_management_system.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailLoanRequest {
    @NotBlank(message = "book copies is required")
    private String bookCopiesId;
}
