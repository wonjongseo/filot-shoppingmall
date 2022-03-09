package com.filot.filotshop.user.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.basket.entity.BasketDTO;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.user.entity.UpdateDTO;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.basket.service.BasketService;
import com.filot.filotshop.user.entity.UserDTO;
import com.filot.filotshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BasketService basketService;


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

