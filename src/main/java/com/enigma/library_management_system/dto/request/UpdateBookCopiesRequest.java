package com.enigma.library_management_system.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookCopiesRequest {
    @NotBlank(message = "book copies id is required")
    private String bookCopiesID;
    @NotBlank(message = "book is required")
    private String bookId;
    @NotBlank(message = "isbn is required")
    private String isbn;
    @NotNull(message = "avilability status is required")
    private Boolean availabilityStatus;
}
