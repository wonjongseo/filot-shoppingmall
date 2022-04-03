package com.filot.filotshop.review.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filot.filotshop.commons.entity.BaseEntity;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {


    public static Review createReview(ReviewForm reviewForm,User user , Product product) {
        Review review = new Review();
        review.setTitle(reviewForm.getTitle());
        review.setContent(reviewForm.getContent());
        review.setRate(reviewForm.getRate());
        review.setUser(user);
        review.setProduct(product);

        return review;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column(columnDefinition = "int(2) default 1")
    private int rate;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "REVIEW_USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_PRODUCT_ID")
    private Product product;

    public void setUser(User user) {
        this.user = user;
        if (!user.getReviews().contains(this)) {
            user.getReviews().add(this);
        }
    }

    public void setProduct(Product product) {
        this.product = product;
        if (!product.getReviews().contains(this)) {
            product.getReviews().add(this);
        }
    }
    public void setRate(int rate) {
        if (rate < 0 || rate > 5) {
            throw new CustomException(ErrorCode.INVALID_REQUEST_RATE);
        }

        this.rate = rate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
