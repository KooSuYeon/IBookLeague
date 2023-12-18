package com.ibookleague.foreignbook;


import com.ibookleague.book.exception.DataFoundException;
import com.ibookleague.foreignbook.domain.ForeignBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ForeignBookService {

    private final ForeignBookRepository foreignBookRepository;

    public List<ForeignBook> getNobelList()
    {
        List<ForeignBook> winNobelBooks = foreignBookRepository.findByWinNobel(true);
        return winNobelBooks;
    }

    public List<ForeignBook> getHugoList()
    {
        List<ForeignBook> winHugoBooks = foreignBookRepository.findByWinHugo(true);
        return winHugoBooks;
    }

    public List<ForeignBook> getwinNobelFalseAndWinHugoFalseList()
    {
        List<ForeignBook> winNobelFalseAndWinHugoFalseBooks = foreignBookRepository.findByWinNobelFalseAndWinHugoFalse();
        return winNobelFalseAndWinHugoFalseBooks;
    }
    public ForeignBook getBook(Integer id)
    {
        Optional<ForeignBook> foreignBook = this.foreignBookRepository.findById(id);
        if (foreignBook.isPresent())
        {
            return foreignBook.get();
        }
        else
        {
            throw new DataFoundException("Book not found");
        }
    }

    public void delete(ForeignBook foreignBook) {
        this.foreignBookRepository.delete(foreignBook);
    }
}
