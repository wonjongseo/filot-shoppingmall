package com.filot.filotshop.config.mail;


import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Random;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "visionwill3322@gmail.com";
    public static final String JOIN_MAIL = "[FILOT SHOP 회원가입 인증]";
    public static final String FIND_PASSWORD_MAIL = "[FILOT SHOP 비밀번호 인증]";

    public static  String createKey(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

//    public String mailSend(MailDto mailDto) {
public String mailSend(String to, String subject) {
        String authKey = createKey();
        try{
            MailHandler mailHandler = new MailHandler(mailSender);

            mailHandler.setTo(to);
            mailHandler.setFrom(MailService.FROM_ADDRESS);
            mailHandler.setSubject(subject);

            String htmlContent="";
            htmlContent+= "<div style='margin:100px;'>";
            htmlContent+= "<h1> 안녕하세요  FILOT-shop입니다!!! </h1>";
            htmlContent+= "<br>";
            htmlContent+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
            htmlContent+= "<br>";
            htmlContent+= "<p>감사합니다!<p>";
            htmlContent+= "<br>";
            htmlContent+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            htmlContent+= "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
            htmlContent+= "<div style='font-size:130%'>";
            htmlContent+= "CODE : <strong>";
            htmlContent+= authKey+"</strong><div> <br/> ";
            htmlContent += "<a href='http://localhost:8080/verify-code'>인증하러 가기</a><br/>";
            htmlContent+= "</div>";
            mailHandler.setText(htmlContent, true);

//            mailHandler.setAttach("newTest.txt", "static/originTest.txt");
//            mailHandler.setInline("filot", "static/img/teamIcon.jpg");

            mailHandler.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return authKey;
    }

}
