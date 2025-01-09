package br.com.litcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.litcatalog.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitle(String title);
}