package com.filot.filotshop.review.service;


import com.filot.filotshop.review.entity.ReviewDTO;
import com.filot.filotshop.review.entity.ReviewForm;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.review.entity.Review;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.review.repository.ReviewRepository;
import com.filot.filotshop.user.repository.UserRepository;
import com.filot.filotshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final UserRepository userRepository;
    private final EntityManager em;

    @Transactional
    public Long removeReview(Long id) {
        Review review = repository.getById(id);
         repository.delete(review);
        return review.getId();
    }

    @Transactional
    public Long createReviewWithUserEmailAndProductId(ReviewForm reviewForm, String userEmail, Long productId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Product product = em.getReference(Product.class, productId);
        Review review = Review.createReview(reviewForm, user, product);

        return repository.save(review).getId();
    }

    public List<ReviewDTO> getReviewDTOsByProductId (Long productId,int page){
        return repository.getAllReviewDTO(productId,page);
    }
}
