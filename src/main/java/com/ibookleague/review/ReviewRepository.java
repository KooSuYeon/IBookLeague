package com.ibookleague.review;

import com.ibookleague.review.domain.Review;
import com.ibookleague.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByUser(User user);
}
