package com.filot.filotshop.order.entity;

import com.filot.filotshop.commons.entity.BaseEntity;
import com.filot.filotshop.basket.entity.Basket;
import com.filot.filotshop.user.entity.Address;
import com.filot.filotshop.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name =  "ORDERS")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
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
    @JoinColumn(name = "ORDER_USER_ID")
    private User user;

    @OneToMany(mappedBy = "productOrder" ,cascade = CascadeType.ALL)
    private List<Basket> baskets = new ArrayList<>();

}
