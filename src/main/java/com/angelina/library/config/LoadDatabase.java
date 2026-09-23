package com.angelina.library.config;

import com.angelina.library.model.Book;
import com.angelina.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Book("The Hobbit", "J.R.R. Tolkien", 1937));
                repository.save(new Book("Dune", "Frank Herbert", 1965));
                System.out.println("Preloaded 2 books");
            }
        };
    }
}