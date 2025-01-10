package br.com.litcatalog.repository;

import br.com.litcatalog.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.litcatalog.models.Book;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String title);
    Optional<Author> findByName(String title);
    Optional<Author> findByIsAlive(int age);
}
