package com.enigma.library_management_system.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewLoanRequest {
    @NotNull(message = "publishe date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent(message = "The publishe date must be in the future or present")
    private LocalDate dueDate;
    @NotBlank(message = "member is required")
    private String memberId;
    @NotEmpty(message = "detail loan is required")
    @Valid
    private List<NewDetailLoanRequest> detailLoans;
}
