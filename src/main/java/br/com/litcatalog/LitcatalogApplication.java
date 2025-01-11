package br.com.litcatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.com.litcatalog.facade.LitCatalogFacade;

import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class LitcatalogApplication implements CommandLineRunner {

	@Autowired
	private LitCatalogFacade litCatalogFacade;

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
			System.out.println("3. List all authors");
			System.out.println("4. List all authors alive in year:");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");
			try {
				int choice = scanner.nextInt();
				scanner.nextLine();
				switch (choice) {

					case 1:
						System.out.print("Enter book title: ");
						String title = scanner.nextLine();
						System.out.println(litCatalogFacade.getBookByTitle(title));
						break;
					case 2:
						litCatalogFacade.getAllBooks().forEach(System.out::println);
						break;
					case 3:
						litCatalogFacade.getAllAuthors().forEach(System.out::println);
						break;
					case 4:
						litCatalogFacade.getAllAliveAuthorsInYear(scanner.nextInt()).forEach(System.out::println);
						break;
					case 6:
						System.out.println("Exiting...");
						scanner.close();
						return;
					default:
						System.out.println("Invalid choice. Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine(); // Clear the invalid input
			}
		}
	}
}