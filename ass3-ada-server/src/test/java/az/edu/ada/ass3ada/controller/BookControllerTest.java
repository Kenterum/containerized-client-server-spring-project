package az.edu.ada.ass3ada.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import az.edu.ada.ass3ada.dto.BookDTO;
import az.edu.ada.ass3ada.entity.Book;
import az.edu.ada.ass3ada.repository.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        bookRepository.deleteAll(); // Clear the database before each test
        // Initialize the database with test data
        Book book = new Book();
        book.setTitle("The Great Gatsby");
        book.setAuthor("F Scott Fitzgerald");
        bookRepository.save(book);
    }

    @Test
    public void testCreateBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("New Book");
        bookDTO.setAuthor("New Author");

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated()) // Expect 201 Created status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("The Great Gatsby"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = bookRepository.findAll().get(0); // Get the first book from the database

        mockMvc.perform(get("/api/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = bookRepository.findAll().get(0); // Get the first book from the database

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Updated Book");
        bookDTO.setAuthor("Updated Author");

        mockMvc.perform(put("/api/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = bookRepository.findAll().get(0); // Get the first book from the database

        mockMvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isNoContent());
    }
}
