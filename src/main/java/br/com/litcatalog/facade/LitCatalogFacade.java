package br.com.litcatalog.facade;

import br.com.litcatalog.exceptions.DuplicateBookException;
import br.com.litcatalog.models.Author;
import br.com.litcatalog.models.Book;
import br.com.litcatalog.service.LitCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LitCatalogFacade {

    @Autowired
    private LitCatalogService litCatalogService;

    public List<String> getAllBooks() {
       return litCatalogService.getAllBooks().stream()
               .map(Book::toString)
               .collect(Collectors.toList());
    }

    public String getBookByTitle(String title) {
        try {
            return litCatalogService.getBookByTitle(title).toString();
        }catch (NullPointerException e){
            return "Book not found";
        }
    }

    public Book saveBook(Book book) {
        try {
            litCatalogService.saveBook(book);
        } catch (DuplicateBookException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBookAsJson(String title) throws IOException {
        return litCatalogService.getBookAsJson(title);
    }

    public List <String> getAllAuthors() {
        return litCatalogService.getAllAuthors().stream()
                .map(Author::toString)
                .collect(Collectors.toList());
    }

    public List <String> getAllAliveAuthorsInYear(int age){
        return litCatalogService.getAllLiveAuthorsInYear(age).stream()
                .map(Author::toString)
                .collect(Collectors.toList());
    }

}