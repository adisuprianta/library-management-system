package com.enigma.library_management_system.service.impl;

import com.enigma.library_management_system.dto.request.NewGenreRequest;
import com.enigma.library_management_system.dto.request.UpdateGenreRequest;
import com.enigma.library_management_system.dto.response.GenreResponse;
import com.enigma.library_management_system.entity.Genre;
import com.enigma.library_management_system.repository.GenreRepository;
import com.enigma.library_management_system.service.GenreService;
import com.enigma.library_management_system.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final ValidationUtil validationUtil;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GenreResponse createNew(NewGenreRequest request) {
        try{
            validationUtil.validate(request);
            Genre genre = Genre.builder()
                    .name(request.getName())
                    .build();
            genreRepository.saveAndFlush(genre);
            return mapToGenreResponse(genre);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"genre already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GenreResponse update(UpdateGenreRequest request) {
        try {
            validationUtil.validate(request);
            Genre genre = getById(request.getGenreId());
            genre.setName(request.getName());
            genreRepository.save(genre);
            return mapToGenreResponse(genre);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"genre already exist");
        }
    }

    @Override
    public void delete(String id) {
        Genre genre = getById(id);
        genreRepository.delete(genre);
    }

    @Override
    public GenreResponse findById(String id) {
        Genre genre = getById(id);
        return mapToGenreResponse(genre);
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "genre not found")
        );
    }
    @Transactional(readOnly = true)
    @Override
    public List<GenreResponse> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(this::mapToGenreResponse).collect(Collectors.toList());
    }

    private GenreResponse mapToGenreResponse(Genre genre) {
        return GenreResponse.builder()
                .genreId(genre.getId())
                .name(genre.getName())
                .build();
    }
}
