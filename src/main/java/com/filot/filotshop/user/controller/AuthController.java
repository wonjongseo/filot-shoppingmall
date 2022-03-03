package com.filot.filotshop.user.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.user.entity.LoginForm;
import com.filot.filotshop.user.entity.JoinForm;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.config.mail.MailService;
import com.filot.filotshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/join")
    @ResponseBody
    public User postJoin(@RequestBody JoinForm userForm) {
        // Change TODO
        if (userForm.getAuthorities().equals("USER"))
            userService.join(userForm);
        else if (userForm.getAuthorities().equals("ADMIN"))
            userService.join(userForm);

        User user = userService.findUserByEmail(userForm.getEmail());
        return user;
    }


    @GetMapping("mail-test-join")
    public String joinForm(){
        return "mail";
    }


    @PostMapping("mail-test-join")
    @ResponseBody
    public void mailJoin(@RequestBody JoinForm userForm , HttpServletRequest request) {
        System.out.println("in mail-test-join userForm = " + userForm);


        userService.duplicateUser(userForm.getEmail());

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("userForm", userForm);

        String authKey = mailService.mailSend(userForm.getEmail(), "[FILOT SHOP 회원가입 인증]");
        System.out.println("in mail-test-join authKey = " + authKey);
        httpSession.setAttribute("authKey", authKey);
        ResponseEntity.status(201);

    }

    @GetMapping("/verify-code")
    public String verifyForm(){
        return "verify";
    }

    @PostMapping("/verify-code")
    @ResponseBody
    public ResponseEntity<String> verifyEmail(@RequestBody Map<String,String> code, HttpServletRequest request) {
        System.out.println("in verify");

        Cookie[] cookies = request.getCookies();

        if(cookies.length > 0){
            for (Cookie cookie : cookies) {
                System.out.println("in verify - cookie = " + cookie);
            }
        }



        System.out.println("code.get(\"code\") = " + code.get("code"));


        HttpSession session = request.getSession(true);

        String authKey = (String) session.getAttribute("authKey");

        JoinForm userForm = (JoinForm) session.getAttribute("userForm");
        System.out.println("userForm = " + userForm);
        User user = null;


        if (authKey != null) {
            if (authKey.equals(code.get("code"))) {
                user = userService.join(userForm);
            }
            else{
                throw new CustomException(ErrorCode.MISMATCH_VERIFY_CODE);
            }
        }
        if (user != null) {

            return ResponseEntity.status(201).body(user.getEmail());
        }
        else {
            throw new CustomException(ErrorCode.MISMATCH_VERIFY_CODE);
        }

    }



    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginForm loginForm) {

        User foundUser = userService.findUserByEmail(loginForm.getEmail());
        if (!passwordEncoder.matches(loginForm.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("비밀번호가 틀려요!!");
        }

        return jwtTokenProvider.createToken(foundUser.getEmail(), foundUser.getRoles());
    }

    @GetMapping("/join/{email}")
    @ResponseBody
    public boolean duplicateEmail(@RequestParam(name = "email") String email) {
        User user = userService.findUserByEmail(email);

        if(user!=null){
            return false;
        }
        return true;
    }

}
