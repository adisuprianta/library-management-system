package com.enigma.library_management_system.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UpdateGenreRequest {
    private String genreId;
    private String name;
}
