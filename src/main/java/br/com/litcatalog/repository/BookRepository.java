package br.com.litcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.litcatalog.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
