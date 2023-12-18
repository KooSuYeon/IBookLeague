package com.ibookleague.foreign_rate;

import com.ibookleague.book.domain.Book;
import com.ibookleague.foreign_rate.domain.ForeignRate;
import com.ibookleague.foreignbook.domain.ForeignBook;
import com.ibookleague.rate.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForeignRateRepository extends JpaRepository<ForeignRate, Integer> {
    Optional<ForeignRate> findByUserAndForeignBook(Long userId, ForeignBook foreignBook);

    List<ForeignRate> findForeignRatesByUser(Long userId);
}
