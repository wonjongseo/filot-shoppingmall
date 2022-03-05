package com.filot.filotshop.user.service;

import com.filot.filotshop.user.entity.JoinForm;
import com.filot.filotshop.user.entity.Address;
import com.filot.filotshop.basket.entity.Basket;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.basket.repository.BasketRepository;
import com.filot.filotshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BasketRepository basketRepository;


    //ok
    public User join(JoinForm form){
        User user = new User();
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRoles(Collections.singletonList("ROLE_"+ form.getAuthorities().toUpperCase()));
        user.setPhoneNumber(form.getPhoneNumber());
        Address address = new Address(form.getDetailAddress(),form.getRoadAddress());
        user.setAddress(address);
        return userRepository.save(user);

    }

    public boolean duplicateUser(String email) {
        boolean isUser = userRepository.findByEmail(email).isPresent();
        return isUser;
    }

    @Transactional(readOnly = true)
    public  User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    
    public Basket findBasketByBasketId( Long basketId){

        return basketRepository.getById(basketId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.INVALID_AUTH_TOKEN));
    }

    @Transactional
    public String changePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(newPassword));
        System.out.println("user.getPassword() = " + user.getPassword());

        return user.getPassword();


    }
}
