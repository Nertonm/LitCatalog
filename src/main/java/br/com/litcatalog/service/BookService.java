package br.com.litcatalog.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class BookService {
    public String SearchBookbyTitle(String title) {
        ConsumoApi consumoApi = new ConsumoApi();
        // Normaliza a string decompondo caracteres acentuados
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD);
        // Expressão regular para remover diacríticos
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        // Remove os diacríticos da string normalizada
        title =  pattern.matcher(normalized).replaceAll("");
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8).replace("+", "%20");
        System.out.println(encodedTitle);
        return consumoApi.getDados("https://gutendex.com/books/?search=" + encodedTitle);
    }
}
