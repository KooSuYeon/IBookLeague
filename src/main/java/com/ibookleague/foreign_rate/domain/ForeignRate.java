package com.ibookleague.foreign_rate.domain;

import com.ibookleague.foreignbook.domain.ForeignBook;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ForeignRate {
    private static String[] a = {"★", "★★", "★★★", "★★★★", "★★★★★"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer rate;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private ForeignBook foreignBook;

    private Long user;
    
    public String getStar() {
        return a[rate - 1];
    }
}
