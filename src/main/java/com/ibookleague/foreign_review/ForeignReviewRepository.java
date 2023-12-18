package com.ibookleague.foreign_review;


import com.ibookleague.foreign_review.domain.ForeignReview;
import com.ibookleague.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForeignReviewRepository extends JpaRepository<ForeignReview, Integer> {
    List<ForeignReview> findAllByUser(User user);
}
