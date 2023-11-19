package com.enigma.library_management_system.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCopiesResponse {
    private String bookCopiesId;
    private String isbn;
    private Boolean availabilityStatus;
    private BookReponse dataBook;
}
