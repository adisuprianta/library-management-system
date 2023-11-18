package com.enigma.library_management_system.service;

import com.enigma.library_management_system.dto.request.NewGenreRequest;
import com.enigma.library_management_system.dto.request.UpdateGenreRequest;
import com.enigma.library_management_system.dto.response.GenreResponse;
import com.enigma.library_management_system.entity.Genre;

import java.util.List;

public interface GenreService {
    GenreResponse createNew(NewGenreRequest request);
    GenreResponse update(UpdateGenreRequest request);
    void delete(String id);
    GenreResponse findById(String id);
    Genre getById(String id);

    List<GenreResponse> findAll();
}
