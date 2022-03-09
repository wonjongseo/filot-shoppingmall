package com.filot.filotshop.basket.entity;


import com.filot.filotshop.commons.entity.BaseEntity;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.order.entity.Order;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.user.entity.User;
import lombok.*;


import javax.persistence.*;

@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Basket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BASKET_Id")
    private Long id;

    @Column(columnDefinition = "int(11) default 1")
    private int productCount;

    @Column(length = 5)
    private String productColor;
    @Column(length = 5)
    private String productSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_PRODUCT_ID")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_USER_ID")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_PRODUCT_ORDER_ID")
    private Order productOrder;

    public int getTotalPrice() {
        if (product != null) {
            return product.getPrice() * this.productCount;
        }
        return 0;
    }

    public void setProductCount(int cnt){
        product.setAmount(product.getAmount() + this.productCount);
        if(this.product.getAmount() < cnt) throw new CustomException(ErrorCode.NOT_ENOUGH_AMOUNT);
        product.setAmount(product.getAmount() - cnt);

        this.productCount = cnt;
    }

    public void setUser(User user) {
        if (!user.getBaskets().contains(this)) {
            user.getBaskets().add(this);
        }
        this.user = user;
    }




}

