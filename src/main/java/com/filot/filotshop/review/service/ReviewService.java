package com.filot.filotshop.review.service;
import com.filot.filotshop.config.s3.S3Service;
import com.filot.filotshop.review.entity.ReviewDTO;
import com.filot.filotshop.review.entity.ReviewForm;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.review.entity.Review;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.review.repository.ReviewRepository;
import com.filot.filotshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final S3Service s3Uploader;
    private final ReviewRepository reviewRepository;
    private final EntityManager em;

    @Transactional
    public Long removeReview(Long id) {
        Review review = reviewRepository.getById(id);
        int i = review.getImageUrl().lastIndexOf("com/");

        String removeImageName = review.getImageUrl().substring(i + 4);
        s3Uploader.delete(removeImageName);

        reviewRepository.deleteById(id);
        return review.getId();
    }




    @Transactional
    public Long createReviewWithUserEmailAndProductId(ReviewForm reviewForm, User user, Long productId, MultipartFile file) {

        Product product = em.getReference(Product.class, productId);
        String url = s3Uploader.upload(file, "review");

        Review review = Review.createReview(reviewForm, user, product);

        review.setImageUrl(url);
        return reviewRepository.save(review).getId();
    }

    public List<ReviewDTO> getReviewDTOListByProductId(Long productId, int page){
        return reviewRepository.getAllReviewDTO(productId,page);
    }

    public List<ReviewDTO> getReviewDTOListByProductId(Long productId){
        return reviewRepository.getAllReviewDTO(productId);
    }
    @Transactional
    public void update(Long reviewId, ReviewForm reviewForm) {

        Review review = reviewRepository.getById(reviewId);
        review.setContent(reviewForm.getContent());
        review.setRate(reviewForm.getRate());
        review.setTitle(reviewForm.getTitle());

    }
}
