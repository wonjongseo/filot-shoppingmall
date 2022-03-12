package com.filot.filotshop.review.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewForm {
    private String content;
    private String title;
    private Integer rate;

}
