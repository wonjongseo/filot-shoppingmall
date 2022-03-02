package com.filot.filotshop.review.controller;

import com.filot.filotshop.review.entity.ReviewDTO;
import com.filot.filotshop.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select NEW com.filot.filotshop.review.entity.ReviewDTO(r.id, r.title,r.rate,r.createAt,u.name) From Product p inner join p.reviews r inner join r.user u where p.id = :id")
    List<ReviewDTO> getReviewDTOsByProductId(@Param("id") Long productID);

}
