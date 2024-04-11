package com.parmeet.books;

import com.parmeet.books.exception.BooksNotFoundException;
import com.parmeet.books.model.BookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/books")
    public ResponseEntity<String> addBook(@RequestBody BookRequest bookRequest) {
        System.out.println("Book added");
         // print book details
        System.out.println("Book id: " + bookRequest.getId());
        System.out.println("Book name: " + bookRequest.getName());
        System.out.println("Book sortIndex: " + bookRequest.getSortIndex());
        return ResponseEntity.ok("Book added");
    }
}
