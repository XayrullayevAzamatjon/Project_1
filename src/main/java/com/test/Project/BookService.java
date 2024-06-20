package com.test.Project;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {


    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooksInReverseOrder() {
        return bookRepository.findAllByOrderByTitleDesc();
    }

    public Book addBook(BookCreateRequest book) {
        Book newBook = new Book();
        newBook.setAuthor(book.author());
        newBook.setDescription(book.description());
        newBook.setTitle(book.title());
        return bookRepository.save(newBook);
    }

    public Map<String, List<Book>> getBooksGroupedByAuthor() {
        List<Book> books = bookRepository.findAll();
        return books.stream().collect(Collectors.groupingBy(Book::getAuthor));
    }


    public List<Map.Entry<String, Long>> getAuthorsByCharacterFrequency(String character) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(character);
        Map<String, Long> authorCount = new HashMap<>();

        for (Book book : books) {
            long count = book.getTitle().chars().filter(ch -> ch == character.charAt(0)).count();
            authorCount.merge(book.getAuthor(), count, Long::sum);
        }

        return authorCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void init() {
        List<Book> books = Arrays.asList(
                new Book("Crime and Punishment", "F. Dostoevsky", null),
                new Book("Anna Karenina", "L. Tolstoy", null),
                new Book("The Brothers Karamazov", "F. Dostoevsky", null),
                new Book("War and Peace", "L. Tolstoy", null),
                new Book("Dead Souls", "N. Gogol", null)
        );
        bookRepository.saveAll(books);
    }
}

