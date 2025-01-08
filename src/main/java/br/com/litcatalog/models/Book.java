package br.com.litcatalog.models;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String author;

    @Enumerated(EnumType.STRING)
    private Language languages;

    private String downloads;
}
