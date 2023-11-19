package com.enigma.library_management_system.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookCopiesRequest {
    private String bookCopiesID;
    private String bookId;
    private String isbn;
    private Boolean availabilityStatus;
}
