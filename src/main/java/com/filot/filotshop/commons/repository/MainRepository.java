package com.filot.filotshop.commons.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MainRepository {
    private final EntityManager em;

}
