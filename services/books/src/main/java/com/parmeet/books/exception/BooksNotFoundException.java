package com.parmeet.books.exception;

public class BooksNotFoundException extends RuntimeException {

    public BooksNotFoundException(String string) {
        super(string);
    }

}
