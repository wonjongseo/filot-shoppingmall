package com.filot.filotshop.controller;

import com.filot.filotshop.user.entity.JoinForm;
import com.filot.filotshop.user.entity.Address;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.admin.service.AdminService;
import com.filot.filotshop.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    public User createUser(){
        User user = new User(); // 20
        user.setName("nameAAAZZ");
        Date date = new Date();
        user.setEmail("wjs12A345");
        user.setPassword(passwordEncoder.encode("dnjswhdtj1!"));
        user.setAddress(new Address("Detail Address", "Road Address"));
        user.setPhoneNumber("0109-0433v2");
        user.setRoles(Collections.singletonList("ROLE_ADMIN"));
        return  user;
    }

    public static JoinForm userForm = JoinForm.builder()
            .authorities("USER")
            .name("nameZZAAZ")
            .email("why")
            .password("passwordZZZ")
            .phoneNumber("pa1-neZZZ2")
            .detailAddress("504-401")
            .roadAddress("HYNIXS")
            .build();
    @Test
    public void 유저_가입한다(){
        userService.join(userForm);
        User joinedUser = userService.findUserByEmail(userForm.getEmail());

        assertThat(joinedUser).isNotNull();
        assertThat(joinedUser.getEmail()).isEqualTo(userForm.getEmail());
    }

    @Test
    public void 동일한_유니크키로_가입_에러난다(){
        userService.join(userForm);
        userForm.setPassword("phoneZZZ2");
        assertThrows(DataIntegrityViolationException.class, () -> userService.join(userForm));

        userForm.setEmail("abcdeq");
        userForm.setPassword("passwordZZZ");
        assertThrows(DataIntegrityViolationException.class, () -> userService.join(userForm));
    }
}
