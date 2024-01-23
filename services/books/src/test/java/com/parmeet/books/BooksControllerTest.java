package com.parmeet.books;

import com.parmeet.books.exception.BooksNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BooksControllerTest {

    @Mock
    private BooksService booksService;

    private BooksController booksController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        booksController = new BooksController(booksService);
    }

    @Test
    void testGetBooks() throws BooksNotFoundException {
        // Arrange
        List<String> expectedBooks = Arrays.asList("Book 1", "Book 2", "Book 3");
        when(booksService.getBooks()).thenReturn(expectedBooks);

        // Act
        ResponseEntity<List<String>> response = booksController.getBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBooks, response.getBody());
    }
}