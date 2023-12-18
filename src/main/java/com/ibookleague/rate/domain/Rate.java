package com.ibookleague.rate.domain;

import com.ibookleague.book.domain.Book;
import com.ibookleague.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Rate {
    private static String[] a = {"★", "★★", "★★★", "★★★★", "★★★★★"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer rate;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Book book;

    private Long user;

    public String getStar() {
        return a[rate - 1];
    }
}
