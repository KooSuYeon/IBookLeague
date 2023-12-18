package com.ibookleague.rate;

import com.ibookleague.book.domain.Book;
import com.ibookleague.book.exception.DataFoundException;
import com.ibookleague.rate.domain.Rate;
import com.ibookleague.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RateService {

    private final RateRepository rateRepository;


    public void create(Book book, Integer rating, User user)
    {
        Rate rate = new Rate();
        rate.setRate(rating);
        rate.setBook(book);
        rate.setUser(user.getUserId());
        rate.setCreateDate(LocalDateTime.now());
        this.rateRepository.save(rate);
    }

    public Rate getRate(Integer id)
    {
        Optional<Rate> rate = this.rateRepository.findById(id);
        if (rate.isPresent()) { return rate.get(); }
        else { throw new DataFoundException("Rate not found"); }
    }

    public void modify(Rate rate, Integer rating)
    {
        rate.setRate(rating);
        rate.setModifyDate(LocalDateTime.now());
        this.rateRepository.save(rate);

    }

    // 변경 전 : 사용자의 엔터티로 hasUserRated를 판단 -> 변경 후 : 사용자의 unique id 로 hasUserRated를 판단
    public boolean hasUserRatedBook(User user, Book book)
    {
        Optional<Rate> existingRate = rateRepository.findByUserAndBook(user.getUserId(), book);

        return existingRate.isPresent();
    }

    public List<Rate> getRateByUserId(Long userId) {
        return rateRepository.findRatesByUser(userId);
    }
}
