package com.filot.filotshop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewForm {
    private Long userId;
    private Long productId;


    private String content;
    private String title;
    private Integer rate;
//    private LocalDateTime updateAt;
//    private LocalDateTime createAt;


}
