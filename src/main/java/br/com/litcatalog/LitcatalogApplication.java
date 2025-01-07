package br.com.litcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.litcatalog.service.*;

@SpringBootApplication
public class LitcatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitcatalogApplication.class, args);
		ConsumoApi consumoApi = new ConsumoApi();
		System.out.println("Consumindo API...");
		System.out.println(consumoApi.getDados("https://gutendex.com/books/"));
	}

}
