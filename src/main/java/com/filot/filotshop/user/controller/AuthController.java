package com.filot.filotshop.user.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.user.entity.*;
import com.filot.filotshop.config.mail.MailService;
import com.filot.filotshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MailService mailService;



    @PostMapping("/join")
    public void mailJoin(@RequestBody JoinForm userForm , HttpServletRequest request) {

        if(userService.duplicateUser(userForm.getEmail())){
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        }

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("userForm", userForm);

        String authKey = mailService.mailSend(userForm.getEmail(), MailService.JOIN_MAIL);
        httpSession.setAttribute("authKey", authKey);
        ResponseEntity.status(201);
    }

    @GetMapping("/join")
    public ResponseEntity<UserDTO> verifyEmail(String code, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        String authKey = (String) session.getAttribute("authKey");
        JoinForm userForm = (JoinForm) session.getAttribute("userForm");

        User user = null;
        if(isVerified(code,authKey))
            user = userService.join(userForm);

        return ResponseEntity.status(201).body(UserDTO.createUserDTO(user));
    }

    private boolean isVerified(String code,  String authKey){

        if (authKey.equals(code) && authKey != null) {
            return true;
        }
        throw new CustomException(ErrorCode.MISMATCH_VERIFY_CODE);
    }

    @GetMapping("/join/dup")
    @ResponseBody
    public ResponseEntity<Boolean> duplicateEmail(String email) {
        boolean existUser = userService.duplicateUser(email);
        if(!existUser){
            return ResponseEntity.status(HttpStatus.OK).body(!existUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(existUser);
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        User foundUser = userService.findUserByEmail(loginForm.getEmail());

        if (!passwordEncoder.matches(loginForm.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("비밀번호가 틀려요!!");
        }

        return jwtTokenProvider.createToken(foundUser.getEmail(), foundUser.getRoles());
    }



}
