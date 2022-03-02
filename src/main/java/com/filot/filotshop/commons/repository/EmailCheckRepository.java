package com.filot.filotshop.commons.repository;

import com.filot.filotshop.commons.entity.EmailCheckDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCheckRepository extends JpaRepository<EmailCheckDTO, Long> {
}
