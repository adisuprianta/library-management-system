package com.enigma.library_management_system.service;

import com.enigma.library_management_system.dto.request.NewBookCopiesRequest;
import com.enigma.library_management_system.dto.request.UpdateBookCopiesRequest;
import com.enigma.library_management_system.dto.response.BookCopiesResponse;

import java.util.List;

public interface BookCopiesService {
    BookCopiesResponse createNew(NewBookCopiesRequest request);
    BookCopiesResponse update(UpdateBookCopiesRequest request);
    BookCopiesResponse getById(String id);
    List<BookCopiesResponse> getAll();
    void delete(String id);
}
