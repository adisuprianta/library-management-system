package com.enigma.library_management_system.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NewBookRequest {
    private String title;
    private String author;
    private LocalDate publishedDate;
    private List<String> listGenreId;
}
