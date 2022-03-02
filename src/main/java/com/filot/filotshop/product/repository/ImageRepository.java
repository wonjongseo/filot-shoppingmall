package com.filot.filotshop.product.repository;

import com.filot.filotshop.product.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
