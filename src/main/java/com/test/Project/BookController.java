package com.test.Project;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooksInReverseOrder();
    }

    @PostMapping
    public Book addBook(@RequestBody BookCreateRequest request) {
        return bookService.addBook(request);
    }

    @GetMapping("/authors")
    public Map<String, List<Book>> getBooksByAuthor() {
        return bookService.getBooksGroupedByAuthor();
    }

    @GetMapping("/search/{character}")
    public List<Map.Entry<String, Long>> getAuthorsByCharacter(@PathVariable String character) {
        return bookService.getAuthorsByCharacterFrequency(character);
    }
}
