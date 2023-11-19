package com.enigma.library_management_system.service;

import com.enigma.library_management_system.dto.request.NewBookRequest;
import com.enigma.library_management_system.dto.request.UpdateBookRequest;
import com.enigma.library_management_system.dto.response.BookReponse;
import com.enigma.library_management_system.entity.Book;

import java.util.List;

public interface BookService {
    BookReponse createNew(NewBookRequest request);
    BookReponse update(UpdateBookRequest request);
    BookReponse getByTitle(String title);
    List<BookReponse> getAll();
    Book getById(String id);
    void deleteById(String id);

}
