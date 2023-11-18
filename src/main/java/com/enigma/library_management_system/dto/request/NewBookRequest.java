package com.enigma.library_management_system.dto.request;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NewBookRequest {
    private String title;
    private String author;
    private String genre;
    private LocalDate publishedDate;
}
