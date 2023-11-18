package com.enigma.library_management_system.controller;

import com.enigma.library_management_system.dto.request.NewBookRequest;
import com.enigma.library_management_system.dto.request.UpdateBookRequest;
import com.enigma.library_management_system.dto.response.BookReponse;
import com.enigma.library_management_system.dto.response.CommonResponse;
import com.enigma.library_management_system.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNew(@RequestBody NewBookRequest request) {
        BookReponse reponse = bookService.createNew(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create book")
                        .data(reponse)
                        .build()
                );
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody UpdateBookRequest request){
        BookReponse reponse = bookService.update(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update book")
                        .data(reponse)
                        .build()
                );
    }

    @GetMapping(path = "/{title}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByName(@PathVariable String title){
        BookReponse reponse = bookService.getByTitle(title);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get book by title")
                        .data(reponse)
                        .build()
                );
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(){
        List<BookReponse> reponses = bookService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all book")
                        .data(reponses)
                        .build()
                );
    }
    @DeleteMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable String id){
        bookService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete book by title")
                        .build()
                );
    }
}
