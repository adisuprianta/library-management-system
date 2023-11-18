package com.enigma.library_management_system.service.impl;

import com.enigma.library_management_system.dto.request.NewBookRequest;
import com.enigma.library_management_system.dto.request.UpdateBookRequest;
import com.enigma.library_management_system.dto.response.BookReponse;
import com.enigma.library_management_system.dto.response.GenreResponse;
import com.enigma.library_management_system.entity.Book;
import com.enigma.library_management_system.entity.Genre;
import com.enigma.library_management_system.repository.BookRepository;
import com.enigma.library_management_system.service.BookService;
import com.enigma.library_management_system.service.GenreService;
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
    private final GenreService genreService;
    @Transactional(rollbackOn = Exception.class)
    @Override
    public BookReponse createNew(NewBookRequest request) {
        try{
            List<Genre> genres = getGenres(request.getListGenreId());

            Book book = Book.builder()
                    .author(request.getAuthor())
                    .title(request.getTitle())
                    .bookGenre(genres)
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
            List<Genre> genres = getGenres(request.getListGenreId());
            Book book = bookRepository.findById(request.getBookId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,"book not found")
            );
            book.setAuthor(request.getAuthor());
            book.setTitle(request.getTitle());
            book.setBookGenre(genres);
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
        List<GenreResponse> genreResponseList = book.getBookGenre().stream().map(genre -> GenreResponse.builder()
                .genreId(genre.getId())
                .name(genre.getName())
                .build()
        ).collect(Collectors.toList());
        return BookReponse.builder()
                .bookId(book.getId())
                .author(book.getAuthor())
                .genres(genreResponseList)
                .publishedDate(book.getPublishedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .title(book.getTitle())
                .build();
    }
    private List<Genre> getGenres(List<String> listGenre) {
        List<Genre> genres = listGenre.stream().map(genreService::getById)
                .collect(Collectors.toList());
        return genres;
    }
}
