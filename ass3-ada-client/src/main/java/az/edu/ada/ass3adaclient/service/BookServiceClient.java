package az.edu.ada.ass3adaclient.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import az.edu.ada.ass3adaclient.dto.BookDTO;

@Service
public class BookServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceClient.class);

    private final RestTemplate restTemplate;

    @Value("${server.url}")
    private String serverUrl;

    public BookServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BookDTO> getAllBooks() {
        logger.info("Calling getAllBooks");
        try {
            List<BookDTO> books = Arrays.asList(restTemplate.getForObject(serverUrl, BookDTO[].class));
            logger.info("getAllBooks response: {}", books);
            return books;
        } catch (Exception e) {
            logger.error("Error in getAllBooks: ", e);
            throw e;
        }
    }

    public BookDTO getBookById(Long id) {
        logger.info("Calling getBookById with id: {}", id);
        try {
            BookDTO book = restTemplate.getForObject(serverUrl + "/" + id, BookDTO.class);
            logger.info("getBookById response: {}", book);
            return book;
        } catch (Exception e) {
            logger.error("Error in getBookById: ", e);
            throw e;
        }
    }

    public BookDTO createBook(BookDTO bookDTO) {
        logger.info("Calling createBook with bookDTO: {}", bookDTO);
        try {
            BookDTO createdBook = restTemplate.postForObject(serverUrl, bookDTO, BookDTO.class);
            logger.info("createBook response: {}", createdBook);
            return createdBook;
        } catch (Exception e) {
            logger.error("Error in createBook: ", e);
            throw e;
        }
    }

    public void updateBook(Long id, BookDTO bookDTO) {
        logger.info("Calling updateBook with id: {}, bookDTO: {}", id, bookDTO);
        try {
            restTemplate.put(serverUrl + "/" + id, bookDTO);
            logger.info("updateBook completed for id: {}", id);
        } catch (Exception e) {
            logger.error("Error in updateBook: ", e);
            throw e;
        }
    }

    public void deleteBook(Long id) {
        logger.info("Calling deleteBook with id: {}", id);
        try {
            restTemplate.delete(serverUrl + "/" + id);
            logger.info("deleteBook completed for id: {}", id);
        } catch (Exception e) {
            logger.error("Error in deleteBook: ", e);
            throw e;
        }
    }
}
