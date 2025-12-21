package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.Author;
import com.example.library.entity.Category;
import com.example.library.dto.CreateBookDTO;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void deleteById(Long id);
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorId(Long authorId);
    List<Book> findAvailableBooks();
    Optional<Book> findByIsbn(String isbn);
    public List<Book> findOverdueBooks();
    public Book createBookWithAuthorAndCategory(CreateBookDTO dto, Author author, Category category);
    public Book updateBook(Long id, CreateBookDTO dto, Author author, Category category);
}