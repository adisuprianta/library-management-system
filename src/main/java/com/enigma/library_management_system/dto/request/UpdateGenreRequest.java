package com.enigma.library_management_system.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UpdateGenreRequest {
    @NotBlank(message = "genre id is required")
    private String genreId;
    @NotBlank(message = "genre name is required")
    private String name;
}
