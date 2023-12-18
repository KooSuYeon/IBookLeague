package com.ibookleague.foreign_review.domain;

import com.ibookleague.foreignbook.domain.ForeignBook;
import com.ibookleague.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ForeignReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private ForeignBook foreignBook;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
