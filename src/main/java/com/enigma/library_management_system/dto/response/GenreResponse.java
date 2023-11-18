package com.enigma.library_management_system.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GenreResponse {
    private String genreId;
    private String name;
}
