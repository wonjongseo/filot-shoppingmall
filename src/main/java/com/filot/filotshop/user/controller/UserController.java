package com.filot.filotshop.user.controller;

import com.filot.filotshop.config.VerifyCode;
import com.filot.filotshop.config.VerifyCodeRepository;
import com.filot.filotshop.config.mail.MailService;
import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.basket.entity.BasketDTO;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.user.entity.UpdateDTO;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.basket.service.BasketService;
import com.filot.filotshop.user.entity.UserDTO;
import com.filot.filotshop.user.service.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BasketService basketService;

    // ok
    private final MailService mailService;
    private final VerifyCodeRepository verifyCodeRepository;


    private final RedisTemplate<String, String> redisTemplate;
    @PostMapping("/password/email")
    public ResponseEntity<String> findPassword(@RequestBody Map<String,String> emailOjb) {

        String email = emailOjb.get("email");

        System.out.println("email = " + email);

        User user = userService.findUserByEmail(email);
        System.out.println("user.getEmail = " + user.getEmail());
        if (user== null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        String authKey = mailService.mailSend(email, MailService.FIND_PASSWORD_MAIL);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set( authKey,user.getEmail());

        String keyAuthKEy = valueOperations.get( authKey);

        System.out.println("keyAuthKEy = " +keyAuthKEy);
        return ResponseEntity.status(200).body(authKey);
    }



    @PostMapping("/password/email/code")
    public ResponseEntity<String> verifyCodeForPassword(@RequestBody UpdateDTO updateDTO) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String valueEmail = valueOperations.get(updateDTO.getCode());

        if (updateDTO.getEmail().equals(valueEmail)) {
            String newPwd = userService.changePassword(updateDTO.getEmail(), updateDTO.getNewPassword());
        } else{
            throw new CustomException(ErrorCode.MISMATCH_VERIFY_CODE);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateDTO.getNewPassword());
    }






    //ok
    @GetMapping("/baskets")
    public List<BasketDTO> showUserBasket(HttpServletRequest request) {

        String userEmail = jwtTokenProvider.getUserEmail(request);
        return basketService.getAllBasket(userEmail);
    }

    // ok
    @PutMapping("/baskets/{baskets-id}")
    public void updateProductCnt(
            HttpServletRequest request,
            @PathVariable(name = "baskets-id") Long basketId, @RequestParam(name = "cnt", required = false) Integer cnt) {
        System.out.println("cnt = " + cnt);
        String userEmail = jwtTokenProvider.getUserEmail(request);
        userService.changeProductCount(userEmail, basketId, cnt);
    }

    @GetMapping("/{user_email}")
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable(name = "user_email") String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(UserDTO.createUserDTO(user));
    }
}

