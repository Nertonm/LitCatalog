package br.com.litcatalog.service;

import br.com.litcatalog.dto.AuthorDTO;
import br.com.litcatalog.models.Author;
import br.com.litcatalog.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.litcatalog.dto.BookDTO;
import br.com.litcatalog.models.Book;
import br.com.litcatalog.repository.BookRepository;
import br.com.litcatalog.exceptions.DuplicateBookException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;

@Service
public class LitCatalogService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public String SearchBookbyTitle(String title) {
        if (bookRepository.existsByTitle(title)) {
            return bookRepository.findByTitle(title).get().toString();
        }
        else{
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
    }

    public String getBookAsJson(String title) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = SearchBookbyTitle(title);
        JsonNode rootNode = mapper.readTree(jsonResponse);
        JsonNode bookNode = rootNode.path("results").get(0);
        BookDTO bookDTO = new BookDTO();
        AuthorDTO authorDTO = new AuthorDTO();
        bookDTO.setTitle(bookNode.path("title").asText());
        bookDTO.setAuthor(bookNode.path("authors").get(0).path("name").asText());
        authorDTO.setName(bookNode.path("authors").get(0).path("name").asText());
        authorDTO.setBirth(Integer.parseInt(bookNode.path("authors").get(0).path("birth_year").asText()));
        int death_year = 0;
        try {
            death_year = Integer.parseInt(bookNode.path("authors").get(0).path("death_year").asText());
        } catch (NumberFormatException e) {
            death_year = 0;
        }
        authorDTO.setDeath(death_year);
        bookDTO.setLanguages((bookNode.path("languages").get(0).asText()));
        bookDTO.setDownloads(bookNode.path("download_count").asText());
        Book book = convertToBook(bookDTO);
        Author author = convertToAuthor(authorDTO);
        if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
            return mapper.writeValueAsString(bookDTO);
        }
        saveBook(book);
        saveAuthor(author);
        return mapper.writeValueAsString(bookDTO);
    }

    public Book getBookAsJson(String title, String title1) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = SearchBookbyTitle(title);
        JsonNode rootNode = mapper.readTree(jsonResponse);
        AuthorDTO authorDTO = new AuthorDTO();
        JsonNode bookNode = rootNode.path("results").get(0);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(bookNode.path("title").asText());
        bookDTO.setAuthor(bookNode.path("authors").get(0).path("name").asText());
        bookDTO.setLanguages((bookNode.path("languages").get(0).asText()));
        bookDTO.setDownloads(bookNode.path("download_count").asText());
        authorDTO.setName(bookNode.path("authors").get(0).path("name").asText());
        authorDTO.setBirth(Integer.parseInt(bookNode.path("authors").get(0).path("birth_year").asText()));
        int death_year = 0;
        try {
            death_year = Integer.parseInt(bookNode.path("authors").get(0).path("death_year").asText());
        } catch (NumberFormatException e) {
            death_year = 0;
        }
        authorDTO.setDeath(death_year);
        Author author = convertToAuthor(authorDTO);
        Book book = convertToBook(bookDTO);
        if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
            return bookRepository.findByTitle(book.getTitle()).get();
        }
        saveBook(book);
        saveAuthor(author);
        return bookRepository.findByTitle(book.getTitle()).get();
    }

    public Book saveBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new DuplicateBookException("Book with title " + book.getTitle() + " already exists.");
        }
        return bookRepository.save(book);
    }

    public Author saveAuthor(Author author) {
        if (authorRepository.existsByName(author.getName())) {
            throw new DuplicateBookException("Author " + author.getName() + " already exists.");
        }
        return authorRepository.save(author);
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
    
    private Author convertToAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setBirth(authorDTO.getBirth());
        author.setDeath(authorDTO.getDeath());
        return author;
    }

    private boolean isValidLanguage(String language) {
        List<String> allowedLanguages = Arrays.asList("en", "pt", "es", "fr", "de");
        return allowedLanguages.contains(language);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getAllLiveAuthorsInYear(int year) {
        List <Author> authors = getAllAuthors();
        authors.removeIf(author -> author.getDeath() < year);
        authors.removeIf(author -> author.getBirth() > year);
        return authors;
    }

    public Book getBookByTitle(String title) {
        try {
            if (bookRepository.findByTitle(title).isPresent()) {
                return  bookRepository.findByTitle(title).get();
            } else{
                System.out.println("Book not found in repository, searching externally...");
                try {
                    return getBookAsJson(title, title);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}