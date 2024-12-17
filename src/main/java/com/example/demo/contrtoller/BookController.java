package com.example.demo.contrtoller;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookSearchParametersDto;
import com.example.demo.dto.CreateBookRequestDto;
import com.example.demo.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.createBook(requestDto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody CreateBookRequestDto requestDto) {
        return bookService.updateBook(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteBook(@PathVariable Long id) {
        bookService.softDeleteBookById(id);
    }

    @GetMapping("/search")
    public List<BookDto> search(
            @RequestParam(required = false) String[] titleParts,
            @RequestParam(required = false) String[] authorParts,
            BookSearchParametersDto searchParameters,
            Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
