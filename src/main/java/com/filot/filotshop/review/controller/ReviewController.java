package com.filot.filotshop.review.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.review.entity.ReviewDTO;
import com.filot.filotshop.review.entity.ReviewForm;
import com.filot.filotshop.basket.entity.Basket;
import com.filot.filotshop.review.entity.Review;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.user.repository.UserRepository;
import com.filot.filotshop.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    // ok
    @PostMapping("/products/{product_id}/reviews")
    public Long addReview(HttpServletRequest rep,
                                    @PathVariable(name = "product_id") Long productId,
                                    @RequestBody ReviewForm reviewForm
    ) {
        String userEmail = jwtTokenProvider.getUserEmail(rep);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Basket> baskets = user.getBaskets();

        boolean flag = false;
        for (Basket basket : baskets) {
            if(basket.getProduct().getId() == productId ){
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new CustomException(ErrorCode.INVALID_REQUEST_REVIEW);
        }
        return reviewService.createReviewWithUserEmailAndProductId(reviewForm, userEmail, productId);
    }

    @GetMapping("/products/{product_id}/reviews")
    public List<ReviewDTO> showReviewByProductId(
            @PathVariable(name = "product_id") Long productId,
            @RequestParam(required = false) int page) {

        return reviewService.getReviewDTOsByProductId(productId, page);
    }

//12
    @PutMapping("/products/{product_id}/reviews/{review_id}")
    public ResponseEntity<ReviewForm> updateReview(
                                    @PathVariable(name = "review_id") Long  review_id,
                                    HttpServletRequest request,
                                    @RequestBody ReviewForm reviewForm
    ) {
        String userEmail = jwtTokenProvider.getUserEmail(request);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Review> reviews = user.getReviews();

        for (Review review : reviews) {
            if (review.getId() == review_id) {
                reviewService.update(review.getId(), reviewForm);
                return ResponseEntity.status(HttpStatus.OK).body(reviewForm);
            }
        }
        throw new CustomException(ErrorCode.NOT_AUTHORIZATION);

    }

    @DeleteMapping("/products/{product_id}/reviews")
    public ResponseEntity<Long> deleteReview(@RequestParam(name = "review_id") Long review_id, HttpServletRequest request) {
        String userEmail = jwtTokenProvider.getUserEmail(request);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Review> reviews = user.getReviews();

        for (Review review : reviews) {
            if (review.getId() == review_id) {
                Long removedId = reviewService.removeReview(review_id);
                return new ResponseEntity<>(removedId, HttpStatus.OK);
            }
        }
        throw new CustomException(ErrorCode.NOT_AUTHORIZATION);
    }

}
