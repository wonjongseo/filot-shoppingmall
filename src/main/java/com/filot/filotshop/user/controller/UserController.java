package com.filot.filotshop.user.controller;

import com.filot.filotshop.config.HerokuRedisService;
import com.filot.filotshop.config.mail.MailService;
import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.basket.entity.BasketDTO;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.user.entity.UpdateDTO;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.basket.service.BasketService;
import com.filot.filotshop.user.entity.UserDTO;
import com.filot.filotshop.user.entity.findPasswordDTO;
import com.filot.filotshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BasketService basketService;
    private final MailService mailService;
    private final RedisTemplate<String, String> redisTemplate;


    @PostMapping("/password/email")
    public ResponseEntity<String> findPassword(@RequestBody Map<String, String> emailOjb, HttpServletRequest request) {

        String email = emailOjb.get("email");

        User user = userService.findUserByEmail(email);

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        String authKey = mailService.mailSend(email, MailService.FIND_PASSWORD_MAIL);
        String host = request.getHeader("host");

//        if (host.equals("localhost:8080")) {
//            System.out.println("In local");
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(authKey, user.getEmail());
//        }
//        else {
//            System.out.println("In heroku");
//            try {
//                Jedis resource = herokuRedisService.jedisPool().getResource();
//                resource.set(authKey, user.getEmail());
//            } catch (URISyntaxException e) {
//                e.print/StackTrace();
//                System.out.println("e.getMessage() = " + e.getMessage());
//            }
//        }

        return ResponseEntity.status(200).body(authKey);
    }



    @PostMapping("/password/email/code")
    public ResponseEntity<String> verifyCodeForPassword(@RequestBody findPasswordDTO updateDTO,HttpServletRequest request) {
        String host = request.getHeader("host");
        String valueEmail = null;
//        if (host.equals("localhost:8080")) {
//            System.out.println("In local" );
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueEmail = valueOperations.get(updateDTO.getCode());    
//        }
//        else {
//            System.out.println("In Heroku");
//            Jedis resource = null;
//            try {
//                resource = herokuRedisService.jedisPool().getResource();
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//                System.out.println("e.getMessage() = " + e.getMessage());
//            }
//            valueEmail = resource.get(updateDTO.getCode());
//        }
        

        if (updateDTO.getEmail().equals(valueEmail)) {
            userService.changePassword(updateDTO.getEmail(), updateDTO.getNewPassword());
        } else{
            throw new CustomException(ErrorCode.MISMATCH_VERIFY_CODE);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateDTO.getNewPassword());
    }

    @PostMapping("/email")
    public String findEmail(@RequestParam String phoneNumber) {
        User user = userService.findUserByPhoneNumber(phoneNumber);

        return user.getPassword();
    }

    //ok
    @GetMapping("/baskets")
    public List<BasketDTO> showUserBasket(HttpServletRequest request) {

        String userEmail = jwtTokenProvider.getUserEmail(request);
        return basketService.getAllBasket(userEmail);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateDTO updateDTO, HttpServletRequest request) {
        String loggedInUserEmail = jwtTokenProvider.getUserEmail(request);
        UserDTO userDTO = userService.updateUser(loggedInUserEmail, updateDTO);

        return ResponseEntity.ok(userDTO);
    }


    @GetMapping("/{user_email}")
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable(name = "user_email") String email, HttpServletRequest request) {
        String loggedInUserEmail = jwtTokenProvider.getUserEmail(request);

        if(!loggedInUserEmail.equals(email)){
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(UserDTO.createUserDTO(user));
    }

}

