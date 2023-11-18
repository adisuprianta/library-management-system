package com.enigma.library_management_system.service.impl;

import com.enigma.library_management_system.dto.request.NewGenreRequest;
import com.enigma.library_management_system.dto.request.UpdateGenreRequest;
import com.enigma.library_management_system.dto.response.GenreResponse;
import com.enigma.library_management_system.entity.Genre;
import com.enigma.library_management_system.repository.GenreRepository;
import com.enigma.library_management_system.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public GenreResponse createNew(NewGenreRequest request) {
        try{
            Genre genre = Genre.builder()
                    .name(request.getName())
                    .build();
            genreRepository.saveAndFlush(genre);
            return mapToGenreResponse(genre);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"genre ini sudah ada");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public GenreResponse update(UpdateGenreRequest request) {
        try {
            Genre genre = getById(request.getGenreId());
            genre.setName(request.getName());
            genreRepository.save(genre);
            return mapToGenreResponse(genre);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"genre is already exist");
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
