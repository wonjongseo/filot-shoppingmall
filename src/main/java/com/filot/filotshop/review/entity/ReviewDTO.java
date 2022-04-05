package com.filot.filotshop.review.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private String title;
    private Float rate;
    private String content;
    private LocalDateTime createdAt;
    private String userName;
    private String imageUrl;
    private String email;
}
