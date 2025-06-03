package com.example.demo.contrtoller;

import com.example.demo.dto.book.BookDto;
import com.example.demo.dto.book.BookSearchParametersDto;
import com.example.demo.dto.book.CreateBookRequestDto;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Get all books",
            description = "Get a paginated list of all available books")
    @GetMapping
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/category/{categoryName}")
    public Page<BookDto> getBooksByCategory(
            @PathVariable String categoryName, Pageable pageable) {
        return bookService.getBooksByCategory(categoryName, pageable);
    }

    @Operation(summary = "Get a book by id", description = "Get a book by searched id")
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @Operation(summary = "Create a book", description = "Create a book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody CreateBookRequestDto requestDto) {
        return bookService.createBook(requestDto);
    }

    @Operation(summary = "Update a book by id", description = "Update a book by its id")
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id,
                              @Valid @RequestBody CreateBookRequestDto requestDto) {
        return bookService.updateBook(id, requestDto);
    }

    @Operation(summary = "Delete a book by id", description = "Mark a book as deleted by its id ")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteBook(@PathVariable Long id) {
        bookService.softDeleteBookById(id);
    }

    @Operation(summary = "Search books by theirs params",
            description = "Search books by theirs params")
    @GetMapping("/search")
    public List<BookDto> search(
            @RequestParam(required = false) String[] titleParts,
            @RequestParam(required = false) String[] authorParts,
            BookSearchParametersDto searchParameters,
            Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
