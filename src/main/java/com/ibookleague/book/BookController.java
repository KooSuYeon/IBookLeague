package com.ibookleague.book;

import com.ibookleague.book.domain.Book;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequestMapping("/ibookleague/book")
@Controller
public class BookController
{

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/ranking")
    public String list(Model model)
    {
        List<Book> bookList = this.bookService.getList();
        Collections.sort(bookList, Comparator.comparing(Book::calculateAvg).reversed());
        model.addAttribute("bookList", bookList);
        return "book_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Book book = this.bookService.getBook(id);
        model.addAttribute("book", book);
        return "book_detail";
    }

    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Book book = this.bookService.getBook(id);
        this.bookService.delete(book);
        return "redirect:/";
    }

    @GetMapping("images/{filename}")
    public ResponseEntity<Resource> showImages(@PathVariable String filename) throws MalformedURLException {

        String path = "koreanBook_image/";
        Resource resource = new ClassPathResource(path + filename + ".png");

        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", "image/png");
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

}
