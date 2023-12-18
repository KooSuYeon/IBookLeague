package com.ibookleague.foreignbook;


import com.ibookleague.foreignbook.domain.ForeignBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForeignBookRepository extends JpaRepository<ForeignBook, Integer> {
    List<ForeignBook> findByWinNobel(Boolean winNobel);
    List<ForeignBook> findByWinHugo(Boolean winHugo);
    List<ForeignBook> findByWinNobelFalseAndWinHugoFalse();
}
