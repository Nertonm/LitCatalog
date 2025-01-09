package br.com.litcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.litcatalog.models.Book;
import br.com.litcatalog.service.*;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LitcatalogApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LitcatalogApplication.class, args);
		BookService bookService = context.getBean(BookService.class);

		try {
			String bookJson = bookService.getBookAsJson("Esaú e Jacó");
			System.out.println(bookJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
