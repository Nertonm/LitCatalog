package br.com.litcatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import br.com.litcatalog.models.Book;
import br.com.litcatalog.service.LitCatalogService;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private LitCatalogService litCatalogService;

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return litCatalogService.getBookById(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return litCatalogService.getAllBooks();
    }
    @GetMapping("/title/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return litCatalogService.getBookByTitle(title);
    }
}