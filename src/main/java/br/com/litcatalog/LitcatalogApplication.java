package br.com.litcatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.com.litcatalog.facade.BookFacade;

import java.util.Scanner;

@SpringBootApplication
public class LitcatalogApplication implements CommandLineRunner {

	@Autowired
	private BookFacade bookFacade;

	public static void main(String[] args) {
		SpringApplication.run(LitcatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		displayMenu();
	}

	private void displayMenu() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("1. Get book by title");
			System.out.println("2. Get all books");
			// System.out.println("3. Save book");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
				case 1:
					System.out.print("Enter book title: ");
					String title = scanner.nextLine();
					System.out.println(bookFacade.getBookByTitle(title));
					break;
				case 2:
					bookFacade.getAllBooks().forEach(System.out::println);
					break;
				case 6:
					System.out.println("Exiting...");
					scanner.close();
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}