package com.enigma.library_management_system.dto.response;

import com.enigma.library_management_system.entity.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookReponse {
    private String bookId;
    private String title;
    private String author;
    private String publishedDate;
    private List<GenreResponse> genres;
}
