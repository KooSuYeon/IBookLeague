package com.ibookleague.book;

import com.ibookleague.book.domain.Book;
import com.ibookleague.book.exception.DataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getList()
    {
        return this.bookRepository.findAll();
    }

    public Book getBook(Integer id)
    {
        Optional<Book> book = this.bookRepository.findById(id);
        if (book.isPresent())
        {
            return book.get();
        }
        else
        {
            throw new DataFoundException("Book not found");
        }
    }

    public void delete(Book book) {
        this.bookRepository.delete(book);
    }
}
