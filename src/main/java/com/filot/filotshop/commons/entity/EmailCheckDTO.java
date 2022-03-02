package com.filot.filotshop.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String code;
}
