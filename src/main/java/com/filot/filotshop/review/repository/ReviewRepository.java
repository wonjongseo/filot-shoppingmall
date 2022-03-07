package com.filot.filotshop.review.repository;

import com.filot.filotshop.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> , CustomReviewRepository {

}
