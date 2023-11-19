package com.enigma.library_management_system.service.impl;

import com.enigma.library_management_system.dto.request.NewBookCopiesRequest;
import com.enigma.library_management_system.dto.request.UpdateBookCopiesRequest;
import com.enigma.library_management_system.dto.response.BookCopiesResponse;
import com.enigma.library_management_system.dto.response.BookReponse;
import com.enigma.library_management_system.dto.response.GenreResponse;
import com.enigma.library_management_system.entity.Book;
import com.enigma.library_management_system.entity.BookCopies;
import com.enigma.library_management_system.repository.BookCopiesRepository;
import com.enigma.library_management_system.service.BookCopiesService;
import com.enigma.library_management_system.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCopiesServiceImpl implements BookCopiesService {

    private final BookCopiesRepository bookCopiesRepository;
    private final BookService bookService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BookCopiesResponse createNew(NewBookCopiesRequest request) {
        try{
            Book book = bookService.getById(request.getBookId());
            BookCopies bookCopies = BookCopies.builder()
                    .book(book)
                    .isbn(request.getIsbn())
                    .availabilityStatus(request.getAvailabilityStatus())
                    .build();
            bookCopiesRepository.saveAndFlush(bookCopies);
            return mapToResponse(bookCopies);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"book copies already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BookCopiesResponse update(UpdateBookCopiesRequest request) {
        try{
            Book book = bookService.getById(request.getBookId());
            BookCopies bookCopies = getBookCopies(request.getBookCopiesID());
            bookCopies.setBook(book);
            bookCopies.setIsbn(request.getIsbn());
            bookCopies.setAvailabilityStatus(request.getAvailabilityStatus());
            bookCopiesRepository.saveAndFlush(bookCopies);
            return  mapToResponse(bookCopies);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "book copies already exist");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public BookCopiesResponse getById(String id) {
        BookCopies bookCopies = getBookCopies(id);
        return mapToResponse(bookCopies);
    }


    @Transactional(readOnly = true)
    @Override
    public List<BookCopiesResponse> getAll() {
        List<BookCopies> bookCopiesList = bookCopiesRepository.findAll();
        return bookCopiesList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        BookCopies bookCopies = getBookCopies(id);
        bookCopiesRepository.delete(bookCopies);
    }

    public BookCopiesResponse mapToResponse(BookCopies bookCopies) {
        List<GenreResponse> genreResponses = bookCopies.getBook().getBookGenre().stream()
                .map(genre ->
                        GenreResponse.builder()
                                .genreId(genre.getId())
                                .name(genre.getName())
                                .build()
                )
                .collect(Collectors.toList());
        BookReponse bookReponse = BookReponse.builder()
                .bookId(bookCopies.getBook().getId())
                .author(bookCopies.getBook().getAuthor())
                .publishedDate(bookCopies.getBook().getPublishedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .title(bookCopies.getBook().getTitle())
                .genres(genreResponses)
                .build();
        return BookCopiesResponse.builder()
                .bookCopiesId(bookCopies.getId())
                .dataBook(bookReponse)
                .availabilityStatus(bookCopies.getAvailabilityStatus())
                .isbn(bookCopies.getIsbn())
                .build();
    }
    private BookCopies getBookCopies(String id) {
        return bookCopiesRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "book copies not found")
        );
    }
}
