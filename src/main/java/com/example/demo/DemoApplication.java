package com.example.demo;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book sampleBook = new Book();
            sampleBook.setTitle("Sample Book");
            sampleBook.setPrice(BigDecimal.valueOf(100));
            bookService.save(sampleBook);
            System.out.println(bookService.findAll());
        };
    }
}
