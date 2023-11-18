package com.enigma.library_management_system.dto.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookReponse {
    private String bookId;
    private String title;
    private String author;
    private String genre;
    private String publishedDate;
}
