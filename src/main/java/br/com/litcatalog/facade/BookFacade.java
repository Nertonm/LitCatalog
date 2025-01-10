package br.com.litcatalog.facade;

import br.com.litcatalog.models.Book;
import br.com.litcatalog.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookFacade {

    @Autowired
    private BookService bookService;

    public List<String> getAllBooks() {
       return bookService.getAllBooks().stream()
               .map(Book::toString)
               .collect(Collectors.toList());
    }

    public String getBookByTitle(String title) {
        try {
            return bookService.getBookByTitle(title).toString();
        }catch (NullPointerException e){
            return "Book not found";
        }
    }

    public Book saveBook(Book book) {
        return bookService.saveBook(book);
    }

    public String getBookAsJson(String title) throws IOException {
        return bookService.getBookAsJson(title);
    }
}