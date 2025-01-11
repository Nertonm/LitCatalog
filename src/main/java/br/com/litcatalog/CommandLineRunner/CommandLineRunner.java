package br.com.litcatalog.CommandLineRunner;

import br.com.litcatalog.facade.LitCatalogFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineRunner {

    @Autowired
    private LitCatalogFacade litCatalogFacade;

    public void start() {
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
                    System.out.println(litCatalogFacade.getBookByTitle(title));
                    break;
                case 2:
                    litCatalogFacade.getAllBooks().forEach(System.out::println);
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