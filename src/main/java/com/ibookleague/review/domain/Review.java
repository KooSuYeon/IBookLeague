package com.ibookleague.review.domain;

import com.ibookleague.book.domain.Book;
import com.ibookleague.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Book book;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
