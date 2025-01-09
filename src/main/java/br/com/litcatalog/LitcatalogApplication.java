package br.com.litcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.litcatalog.models.Book;
import br.com.litcatalog.service.*;

@SpringBootApplication
public class LitcatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitcatalogApplication.class, args);
		ConsumoApi consumoApi = new ConsumoApi();
		Book book = new Book();
		System.out.println("Consumindo API...");
		BookService bookService = new BookService();
		try {
			String bookJson = bookService.getBookAsJson("Esaú e Jacó");
			System.out.println(bookJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
