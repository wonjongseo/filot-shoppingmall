package com.filot.filotshop.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.dto.user.LoginForm;
import com.filot.filotshop.dto.user.JoinForm;
import com.filot.filotshop.entity.User;
import com.filot.filotshop.service.MailService;
import com.filot.filotshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public void mailJoin(JoinForm userForm , HttpServletRequest request) {
        userService.duplicateUser(userForm.getEmail());


        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("userForm", userForm);

        String authKey = mailService.mailSend(userForm.getEmail(), "[FILOT SHOP 회원가입 인증]");
        httpSession.setAttribute("authKey", authKey);


    }
    @GetMapping("/verify-code")
    public String verifyForm(){
        return "verify";
    }

    @PostMapping("/verify-code")
    @ResponseBody
    public String verifyEmail(@RequestBody String code, HttpServletRequest request) {

        String[] strings = code.split("=");

        HttpSession session = request.getSession(true);
        String authKey = (String) session.getAttribute("authKey");

        JoinForm userForm = (JoinForm) session.getAttribute("userForm");
        User user = null;

        if (authKey != null) {
            if (authKey.equals(strings[1])) {
                user = userService.join(userForm);
            }
        }


        return user != null ? user.getEmail() : null;
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
    @GetMapping("/aaaa")
    @ResponseBody
    public String aaaa(HttpServletRequest request){
        String s = jwtTokenProvider.resolveToken(request);
        String userPk = jwtTokenProvider.getUserPk(s);

        System.out.println("s = " + s);
        System.out.println("userPk = " + userPk);
        return s;
    }
}
