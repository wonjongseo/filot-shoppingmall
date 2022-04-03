package com.filot.filotshop.review.repository;
import com.filot.filotshop.review.entity.ReviewDTO;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository{


    private static int SHOW_REVIEW_COUNT = 3;

    private final EntityManager em;

    public final String SELECT_REVIEWDTO = "select r.id, r.title,r.rate,r.createAt,u.name ,r.imageUrl";
    private List<ReviewDTO> createReviewDTO(List<Object[]> reviews){

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Object[] review : reviews) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId((Long) review[0]);
            reviewDTO.setTitle((String) review[1]);
            reviewDTO.setRate((Integer) review[2]);
            reviewDTO.setCreatedAt((LocalDateTime) review[3]);
            reviewDTO.setUserName((String) review[4]);
            reviewDTO.setImageUrl((String) review[5]);
            reviewDTOS.add(reviewDTO);
        }

        return reviewDTOS;
    }


    @Override
    public List<ReviewDTO> getAllReviewDTO(Long productId,int page) {
        System.out.println("page = " + page);
        
        List<Object[]> reviewDTOS = em.createQuery(SELECT_REVIEWDTO + " From Review r inner join r.user u inner join  r.product p where  p.id = :productId")
                .setParameter("productId", productId)
                .setFirstResult((page - 1) * SHOW_REVIEW_COUNT)
                .setMaxResults( SHOW_REVIEW_COUNT)
                .getResultList();

        System.out.println("reviewDTOS.size() = " + reviewDTOS.size());
        return  createReviewDTO(reviewDTOS);
    }


}

