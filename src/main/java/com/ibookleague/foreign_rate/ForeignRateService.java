package com.ibookleague.foreign_rate;


import com.ibookleague.book.domain.Book;
import com.ibookleague.book.exception.DataFoundException;
import com.ibookleague.foreign_rate.domain.ForeignRate;
import com.ibookleague.foreignbook.domain.ForeignBook;
import com.ibookleague.rate.domain.Rate;
import com.ibookleague.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ForeignRateService {

    private final ForeignRateRepository foreignRateRepository;

    public void create(ForeignBook foreignBook, Integer rating, User user)
    {
        ForeignRate foreignRate = new ForeignRate();
        foreignRate.setRate(rating);
        foreignRate.setForeignBook(foreignBook);
        foreignRate.setUser(user.getUserId());
        foreignRate.setCreateDate(LocalDateTime.now());
        this.foreignRateRepository.save(foreignRate);
    }

    public ForeignRate getRate(Integer id)
    {
        Optional<ForeignRate> foreignRate = this.foreignRateRepository.findById(id);
        if (foreignRate.isPresent()) { return foreignRate.get(); }
        else { throw new DataFoundException("Rate not found"); }
    }

    public void modify(ForeignRate foreignRate, Integer rating)
    {
        foreignRate.setRate(rating);
        foreignRate.setModifyDate(LocalDateTime.now());
        this.foreignRateRepository.save(foreignRate);

    }

    // 변경 전 : 사용자의 엔터티로 hasUserRated를 판단 -> 변경 후 : 사용자의 unique id 로 hasUserRated를 판단
    public boolean hasUserRatedBook(User user, ForeignBook foreignBook)
    {
        Optional<ForeignRate> existingRate = foreignRateRepository.findByUserAndForeignBook(user.getUserId(), foreignBook);

        return existingRate.isPresent();
    }

    public List<ForeignRate> getRateByUserId(Long userId) {
        return foreignRateRepository.findForeignRatesByUser(userId);
    }
}
