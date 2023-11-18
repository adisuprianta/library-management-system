package com.enigma.library_management_system.controller;

import com.enigma.library_management_system.dto.request.NewGenreRequest;
import com.enigma.library_management_system.dto.request.UpdateGenreRequest;
import com.enigma.library_management_system.dto.response.CommonResponse;
import com.enigma.library_management_system.dto.response.GenreResponse;
import com.enigma.library_management_system.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNew(@RequestBody NewGenreRequest request){
        GenreResponse response = genreService.createNew(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create genre")
                        .data(response)
                        .build()
                );
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody UpdateGenreRequest request){
        GenreResponse response = genreService.update(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update genre")
                        .data(response)
                        .build()
                );
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        List<GenreResponse> responses = genreService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all genre")
                        .data(responses)
                        .build()
                );
    }
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> geById(@PathVariable String id){
        GenreResponse response = genreService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get genre by id" )
                        .data(response)
                        .build()
                );
    }
    @DeleteMapping(path = "/{id}")
    private ResponseEntity<?> delete(@PathVariable String id){
        genreService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete genre")
                        .build()
                );
    }
}
