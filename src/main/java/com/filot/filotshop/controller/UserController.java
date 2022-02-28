package com.filot.filotshop.controller;

import com.filot.filotshop.config.secuity.JwtTokenProvider;
import com.filot.filotshop.dto.basket.BasketDTO;
import com.filot.filotshop.entity.User;
import com.filot.filotshop.repository.UserRepository;
import com.filot.filotshop.service.BasketService;
import com.filot.filotshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

  // ok
    @PostMapping("/check-mail")
    public ResponseEntity<String> checkEmail(@RequestParam(name = "email") String email) {
        User user = userService.findUserByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(email, HttpStatus.OK);
        }
        return new ResponseEntity<>(email,HttpStatus.BAD_REQUEST);
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
        userService.changeProductCount(userEmail,basketId, cnt);
    }

}
