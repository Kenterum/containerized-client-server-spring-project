package az.edu.ada.ass3ada.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import az.edu.ada.ass3ada.dto.BookDTO;
import az.edu.ada.ass3ada.entity.Book;
import az.edu.ada.ass3ada.repository.BookRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("The Great Gatsby");
        bookDTO.setAuthor("F. Scott Fitzgerald");
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);

        List<BookDTO> books = bookService.getAllBooks();
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(bookDTO, books.get(0));
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);

        BookDTO foundBook = bookService.getBookById(1L);
        assertNotNull(foundBook);
        assertEquals(bookDTO, foundBook);
    }

    @Test
    public void testCreateBook() {
        when(bookRepository.existsByTitle("The Great Gatsby")).thenReturn(false);
        when(modelMapper.map(any(BookDTO.class), eq(Book.class))).thenReturn(book);
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO createdBook = bookService.createBook(bookDTO);
        assertNotNull(createdBook);
        assertEquals(bookDTO, createdBook);
    }

    @Test
    public void testCreateBookWithDuplicateTitle() {
        when(bookRepository.existsByTitle("The Great Gatsby")).thenReturn(true);

        Exception exception = assertThrows(ValidationException.class, () -> {
            bookService.createBook(bookDTO);
        });

        String expectedMessage = "A book with the same title already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);

        BookDTO updatedBook = bookService.updateBook(1L, bookDTO);
        assertNotNull(updatedBook);
        assertEquals(bookDTO, updatedBook);
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
