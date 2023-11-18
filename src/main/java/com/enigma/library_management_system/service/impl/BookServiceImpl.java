package com.enigma.library_management_system.service.impl;

import com.enigma.library_management_system.dto.request.NewBookRequest;
import com.enigma.library_management_system.dto.request.UpdateBookRequest;
import com.enigma.library_management_system.dto.response.BookReponse;
import com.enigma.library_management_system.entity.Book;
import com.enigma.library_management_system.repository.BookRepository;
import com.enigma.library_management_system.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BookReponse createNew(NewBookRequest request) {
        try{
            Book book = Book.builder()
                    .author(request.getAuthor())
                    .title(request.getTitle())
                    .genre(request.getGenre())
                    .publishedDate(request.getPublishedDate())
                    .build();
            bookRepository.saveAndFlush(book);
            return mapToBookResponse(book);

        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "book already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BookReponse update(UpdateBookRequest request) {
        try{
            Book book = bookRepository.findById(request.getBookId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,"book not found")
            );
            book.setAuthor(request.getAuthor());
            book.setTitle(request.getTitle());
            book.setGenre(request.getGenre());
            book.setPublishedDate(request.getPublishedDate());
            bookRepository.saveAndFlush(book);
            return mapToBookResponse(book);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "title book already exist");
        }
    }

    @Override
    public BookReponse getByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"book not found")
        );
        return mapToBookResponse(book);
    }

    @Override
    public List<BookReponse> getAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapToBookResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found")
        );
        bookRepository.delete(book);
    }
    private BookReponse mapToBookResponse(Book book) {
        return BookReponse.builder()
                .bookId(book.getId())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .publishedDate(book.getPublishedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .title(book.getTitle())
                .build();
    }
}
