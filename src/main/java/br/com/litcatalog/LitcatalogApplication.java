package br.com.litcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.litcatalog.models.Book;
import br.com.litcatalog.service.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class LitcatalogApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LitcatalogApplication.class, args);
		BookService bookService = context.getBean(BookService.class);

		System.out.println(bookService.getBookByTitle("Great Expectations").toString());
		System.out.println(bookService.getBookByTitle("Great Expectations").toString());
	}
}
