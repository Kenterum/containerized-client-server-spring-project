package az.edu.ada.ass3adaclient.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.edu.ada.ass3adaclient.dto.BookDTO;
import az.edu.ada.ass3adaclient.service.BookServiceClient;

@RestController
@RequestMapping("/api/books")
public class BookClientController {

    private static final Logger logger = LoggerFactory.getLogger(BookClientController.class);

    @Autowired
    private BookServiceClient bookServiceClient;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        logger.info("Received request to getAllBooks");
        return bookServiceClient.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        logger.info("Received request to getBookById with id: {}", id);
        return bookServiceClient.getBookById(id);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        logger.info("Received request to createBook with bookDTO: {}", bookDTO);
        return bookServiceClient.createBook(bookDTO);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        logger.info("Received request to updateBook with id: {}, bookDTO: {}", id, bookDTO);
        bookServiceClient.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        logger.info("Received request to deleteBook with id: {}", id);
        bookServiceClient.deleteBook(id);
    }
}
