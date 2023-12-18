package com.ibookleague.review;


import com.ibookleague.book.domain.Book;
import com.ibookleague.review.domain.Review;
import com.ibookleague.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public void create(Book book, String content, User user)
    {
        Review review = new Review();
        review.setContent(content);
        review.setCreateDate(LocalDateTime.now());
        review.setBook(book);
        review.setUser(user);
        this.reviewRepository.save(review);
    }
}
