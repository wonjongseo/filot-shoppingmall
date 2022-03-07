package com.filot.filotshop.review.repository;

import com.filot.filotshop.review.entity.ReviewDTO;

import java.util.List;

public interface CustomReviewRepository {
     List<ReviewDTO> getAllReviewDTO(Long productId,int page);
}
