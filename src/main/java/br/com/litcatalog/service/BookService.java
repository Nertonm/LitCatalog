package br.com.litcatalog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.litcatalog.dto.BookDTO;
import br.com.litcatalog.models.Book;
import br.com.litcatalog.repository.BookRepository;
import br.com.litcatalog.exception.DuplicateBookException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public String SearchBookbyTitle(String title) {
        ConsumoApi consumoApi = new ConsumoApi();

        // Normalize the string by decomposing accented characters
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD);

        // Regular expression to remove diacritics
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        // Remove diacritics from the normalized string
        title = pattern.matcher(normalized).replaceAll("");
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8).replace("+", "%20");
        System.out.println(encodedTitle);
        return consumoApi.getDados("https://gutendex.com/books/?search=" + encodedTitle);
    }

    public String getBookAsJson(String title) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = SearchBookbyTitle(title);
        JsonNode rootNode = mapper.readTree(jsonResponse);
        JsonNode bookNode = rootNode.path("results").get(0);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(bookNode.path("title").asText());
        bookDTO.setAuthor(bookNode.path("authors").get(0).path("name").asText());
        bookDTO.setLanguages((bookNode.path("languages").get(0).asText()));
        bookDTO.setDownloads(bookNode.path("download_count").asText());
        Book book = convertToBook(bookDTO);
        saveBook(book);
        return mapper.writeValueAsString(bookDTO);
    }

    public Book saveBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new DuplicateBookException("Book with title " + book.getTitle() + " already exists.");
        }
        return bookRepository.save(book);
    }

    private Book convertToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());

        // Validate the languages field
        String language = bookDTO.getLanguages();
        if (!isValidLanguage(language)) {
            throw new IllegalArgumentException("Invalid language: " + language);
        }
        book.setLanguages(language);

        book.setDownloads(bookDTO.getDownloads());
        return book;
    }

    private boolean isValidLanguage(String language) {
        // Define the allowed languages based on the database constraint
        List<String> allowedLanguages = Arrays.asList("en", "pt", "es", "fr", "de");
        return allowedLanguages.contains(language);
    }
}