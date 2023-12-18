package com.ibookleague.foreignbook.domain;

import com.ibookleague.foreign_rate.domain.ForeignRate;
import com.ibookleague.foreign_review.domain.ForeignReview;
import com.ibookleague.rate.domain.Rate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class ForeignBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String author;


    private Boolean winNobel;
    private Integer winNobelYear;

    private Boolean winHugo;
    private Integer winHugoYear;

    @OneToMany(mappedBy = "foreignBook", cascade = CascadeType.REMOVE)
    private List<ForeignRate> rateList;

    public Double calculateAvg()
    {
        if(rateList == null || rateList.isEmpty()) { return 0.0; }
        Integer sum = 0;
        for(ForeignRate foreignRate:rateList) { sum += foreignRate.getRate(); }

        double average = (double) sum / rateList.size();
        return Math.round(average * 10.0) / 10.0;
    }

    @OneToMany(mappedBy = "foreignBook", cascade = CascadeType.REMOVE)
    private List<ForeignReview> reviewList;
}
