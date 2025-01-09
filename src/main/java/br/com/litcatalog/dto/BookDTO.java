package br.com.litcatalog.dto;

import br.com.litcatalog.models.Language;

public class BookDTO {
    private String title;
    private String author;
    private String languages;
    private String downloads;

    public String getDownloads() {
        return downloads;
    }

    public String getLanguages() {
        return languages;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
