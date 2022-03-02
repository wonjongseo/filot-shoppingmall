package com.filot.filotshop.commons.entity;

import com.filot.filotshop.commons.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Discount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISCOUNT_ID")
    private Long id;

    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime startDate;

    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime endDate;

    private int rate;










}


