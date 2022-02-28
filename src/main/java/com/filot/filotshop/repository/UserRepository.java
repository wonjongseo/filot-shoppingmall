package com.filot.filotshop.repository;

import com.filot.filotshop.entity.Basket;
import com.filot.filotshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);



    @Query("select b from Basket b  WHERE b.user.email = :userEmail and b.id = :basketId")
    Basket findBasketEmailIdAndBasketId(Long basketId, String userEmail);

}
