package br.com.litcatalog.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.runtime.ObjectMethods;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import br.com.litcatalog.dto.BookDTO;
import br.com.litcatalog.models.Book;
import br.com.litcatalog.repository.BookRepository;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public String SearchBookbyTitle(String title) {
        ConsumoApi consumoApi = new ConsumoApi();

        // Normaliza a string decompondo caracteres acentuados
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD);

        // Expressão regular para remover diacríticos
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        // Remove os diacríticos da string normalizada
        title =  pattern.matcher(normalized).replaceAll("");
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8).replace("+", "%20");
        System.out.println(encodedTitle);
        return consumoApi.getDados("https://gutendex.com/books/?search=" + encodedTitle);
    }

    public String getBookAsJson(String tittle) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = SearchBookbyTitle(tittle);
        JsonNode rootNode = mapper.readTree(jsonResponse);
        JsonNode bookNode = rootNode.path("results").get(0);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(bookNode.path("title").asText());
        bookDTO.setAuthor(bookNode.path("author").asText());
        bookDTO.setLanguages(bookNode.path("languages").asText());
        bookDTO.setDownloads(bookNode.path("downloads").asText());
        saveBook(bookDTO);
        return mapper.writeValueAsString(bookDTO);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
