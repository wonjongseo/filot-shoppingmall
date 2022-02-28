package com.filot.filotshop.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.dto.ReviewDTO;
import com.filot.filotshop.dto.ReviewForm;
import com.filot.filotshop.entity.Basket;
import com.filot.filotshop.entity.Review;
import com.filot.filotshop.entity.User;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.repository.UserRepository;
import com.filot.filotshop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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


//ok
    @DeleteMapping("/products/{product_id}/reviews")
    public ResponseEntity<Long> deleteReview(@RequestParam(name = "review_id") Long review_id) {
        Long removedId = reviewService.removeReview(review_id);

        return new ResponseEntity<>(removedId, HttpStatus.OK);
    }

    @GetMapping("/products/{product_id}/reviews")
    public List<ReviewDTO> showReviewByProductId(@PathVariable(name="product_id") Long productId){
        return reviewService.getReviewDTOsByProductId(productId);
    }
     // 또 이거 api 바꿔야해 너무 구려
    @PutMapping("/products/{product_id}/reviews/{review_id}")
    public ResponseEntity updateReview(
                                    @PathVariable(name = "product_id") Long productId,
                                    @PathVariable(name = "review_id") Review review,
                                    @RequestBody ReviewForm reviewForm
    ) {
        /*
        이게 서버 단에서 기존 내용이랑 변경 내용을 조건문으로 체크해서 변경해줘야하나 ?
        아니면 프론트에서 변경된 내용만 알려줄수 있을까 ?
        변경된 내용만 알려준다면 난 파라미터를 어떻게 받아야하지 ?
        이거 어캐함 ㅡ,ㅡ
         */
        System.out.println("review.getContent() = " + review.getContent());
        review.setContent(reviewForm.getContent());

        return new ResponseEntity(HttpStatus.OK);
    }

    //{{base}}/products/{id}/reviews
}
