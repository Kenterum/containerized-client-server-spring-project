package az.edu.ada.ass3ada.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import az.edu.ada.ass3ada.dto.BookDTO;
import az.edu.ada.ass3ada.entity.Book;
import az.edu.ada.ass3ada.repository.BookRepository;
import jakarta.validation.ValidationException;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<BookDTO> getAllBooks() {
        long startTime = System.currentTimeMillis();
        logger.info("Called method: getAllBooks");

        List<BookDTO> result = bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        logger.info("Executed method: getAllBooks with result: {}", result);
        logger.info("Execution time: {} ms", (endTime - startTime));
        return result;
    }

    public BookDTO getBookById(Long id) {
        long startTime = System.currentTimeMillis();
        logger.info("Called method: getBookById with argument: {}", id);

        BookDTO result = modelMapper.map(bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found")), BookDTO.class);

        long endTime = System.currentTimeMillis();
        logger.info("Executed method: getBookById with result: {}", result);
        logger.info("Execution time: {} ms", (endTime - startTime));
        return result;
    }

    public BookDTO createBook(BookDTO bookDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("Called method: createBook with argument: {}", bookDTO);

        if (bookRepository.existsByTitle(bookDTO.getTitle())) {
            throw new ValidationException("A book with the same title already exists");
        }

        Book book = modelMapper.map(bookDTO, Book.class);
        BookDTO result = modelMapper.map(bookRepository.save(book), BookDTO.class);

        long endTime = System.currentTimeMillis();
        logger.info("Executed method: createBook with result: {}", result);
        logger.info("Execution time: {} ms", (endTime - startTime));
        return result;
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("Called method: updateBook with arguments: {}, {}", id, bookDTO);

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (bookRepository.existsByTitle(bookDTO.getTitle()) && !existingBook.getTitle().equals(bookDTO.getTitle())) {
            throw new ValidationException("A book with the same title already exists");
        }

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        BookDTO result = modelMapper.map(bookRepository.save(existingBook), BookDTO.class);

        long endTime = System.currentTimeMillis();
        logger.info("Executed method: updateBook with result: {}", result);
        logger.info("Execution time: {} ms", (endTime - startTime));
        return result;
    }

    public void deleteBook(Long id) {
        long startTime = System.currentTimeMillis();
        logger.info("Called method: deleteBook with argument: {}", id);

        bookRepository.deleteById(id);

        long endTime = System.currentTimeMillis();
        logger.info("Executed method: deleteBook");
        logger.info("Execution time: {} ms", (endTime - startTime));
    }
}
