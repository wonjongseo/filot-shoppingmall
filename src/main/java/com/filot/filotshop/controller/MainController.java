package com.filot.filotshop.controller;

import com.filot.filotshop.dto.ProductsCategoriesDTO;
import com.filot.filotshop.entity.User;
import com.filot.filotshop.repository.UserRepository;
import com.filot.filotshop.service.CategoryService;
import com.filot.filotshop.service.MainService;
import com.filot.filotshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MainService mainService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<ProductsCategoriesDTO> main(){
        ProductsCategoriesDTO mainPage = mainService.getProductsAndCategories();

        return ResponseEntity.ok(mainPage);
    }
    @GetMapping("/did")
    public String did(){
        ProductsCategoriesDTO mainPage = mainService.getProductsAndCategories();

        return "DID WORD ?";
    }

    @GetMapping("/test-user")
    public List<User> showuser(){

         return userRepository.findAll();
    }



}
