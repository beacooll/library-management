package com.example.library.service.impl;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.dto.CreateBookDTO;
import com.example.library.dto.UserDTO;
import com.example.library.dto.BookDTO;
import com.example.library.entity.User;
import com.example.library.entity.Author;
import com.example.library.entity.Category;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
    @Override
    public List<Book> findByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);

    }
    @Override
    public List<Book> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
  

    @Override
    public List<Book> findAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }
    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    @Override
    public List<Book> findOverdueBooks() {
        return bookRepository.findOverdueBooks();
    }
    
    public Book createBookWithAuthorAndCategory(CreateBookDTO dto, Author author, Category category) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationDate(dto.getPublicationDate());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setAuthor(author);
        book.setCategory(category);
        return save(book);
    }
    @Override
    @Transactional
    public Book updateBook(Long id, CreateBookDTO dto, Author author, Category category) {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));

        existingBook.setTitle(dto.getTitle());
        existingBook.setIsbn(dto.getIsbn());
        existingBook.setPublicationDate(dto.getPublicationDate());
        existingBook.setAvailableCopies(dto.getAvailableCopies());
        existingBook.setAuthor(author);
        existingBook.setCategory(category);

        return bookRepository.save(existingBook);
    }
}