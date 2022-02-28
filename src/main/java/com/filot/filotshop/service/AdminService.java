package com.filot.filotshop.service;

import com.filot.filotshop.entity.User;
import com.filot.filotshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public List<User> findUserAll(){
        return userRepository.findAll();
    }
}
