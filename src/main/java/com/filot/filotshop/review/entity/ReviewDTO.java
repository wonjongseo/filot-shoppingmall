package com.filot.filotshop.review.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ReviewDTO {
    private Long id;
    private String title;
    private int rate;
    private LocalDateTime createdAt;
    private String userName;
}
