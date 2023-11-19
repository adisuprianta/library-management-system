package com.enigma.library_management_system.controller;

import com.enigma.library_management_system.dto.request.NewBookCopiesRequest;
import com.enigma.library_management_system.dto.request.UpdateBookCopiesRequest;
import com.enigma.library_management_system.dto.response.BookCopiesResponse;
import com.enigma.library_management_system.dto.response.CommonResponse;
import com.enigma.library_management_system.service.BookCopiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-copies")
@RequiredArgsConstructor
public class BookCopiesController {
    private final BookCopiesService bookCopiesService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNew(@RequestBody NewBookCopiesRequest request) {
        BookCopiesResponse response = bookCopiesService.createNew(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create book copies")
                        .data(response)
                        .build()
                );
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody UpdateBookCopiesRequest request) {
        BookCopiesResponse response = bookCopiesService.update(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update book copies")
                        .data(response)
                        .build()
                );
    }
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@PathVariable String id){
        BookCopiesResponse response = bookCopiesService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get book copies by id")
                        .data(response)
                        .build()
                );
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        List<BookCopiesResponse> responses = bookCopiesService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all book copies")
                        .data(responses)
                        .build()
                );
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        bookCopiesService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete book copies")
                        .build()
                );
    }
}
