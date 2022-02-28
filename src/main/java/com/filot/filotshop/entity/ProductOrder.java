package com.filot.filotshop.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.ObjectIdGenerators.*;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = IntSequenceGenerator.class, property = "id")
public class ProductOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ORDER_ID")
    private Long id;

    @Embedded
    private Address address;

    @Lob
    String deliveryMassage;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(length = 1)
    private String refundStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ORDER_USER_ID")
    private User user;

    @OneToMany(mappedBy = "productOrder" ,cascade = CascadeType.ALL)
    private List<Basket> baskets = new ArrayList<>();

}
