package com.parmeet.books;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parmeet.books.exception.BooksNotFoundException;

@RestController
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    /**
        * Retrieves a list of books.
        *
        * @return ResponseEntity<List<String>> The response entity containing the list of books.
        * @throws BooksNotFoundException If no books are found.
        */
    @GetMapping("/books")
    public ResponseEntity<List<String>> getBooks() {
        var books = booksService.getBooks();
        if (books.isEmpty()) throw new BooksNotFoundException("Books not found");
        return ResponseEntity.ok(books);
    }
}
