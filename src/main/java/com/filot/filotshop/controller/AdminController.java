package com.filot.filotshop.controller;

import com.filot.filotshop.commons.S3Uploader;
import com.filot.filotshop.dto.UserDTO;
import com.filot.filotshop.dto.product.DetailProductDTO;
import com.filot.filotshop.entity.*;
import com.filot.filotshop.dto.category.CategoryForm;
import com.filot.filotshop.dto.product.ProductForm;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.repository.product.ImageRepository;
import com.filot.filotshop.repository.UserRepository;
import com.filot.filotshop.service.CategoryService;
import com.filot.filotshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;

    @GetMapping("/user-list")
    public ResponseEntity<Page<User>> findUserList(@PageableDefault(size = 10, sort = "createAt") Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user-list-all")
    public List<UserDTO> findUserAll(){

        List<UserDTO> userDTOs = new ArrayList<>();

        userRepository.findAll().stream()
                .forEach(user -> userDTOs.add(UserDTO.createUserDTO(user)));

        return userDTOs;
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> postCategory(@RequestBody CategoryForm form) {
        Category category = categoryService.addCategory(form);
        return ResponseEntity.ok(category);
    }


    @PostMapping("/products/image")
    public ResponseEntity<String> uploads(
            @RequestParam("id") Product product ,
            @RequestParam("category_name") String categoryName,
            MultipartFile[] files)  {


        for (MultipartFile file : files) {

            File checkFile = new File(file.getOriginalFilename());

            MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

            String mimeType = mimetypesFileTypeMap.getContentType(checkFile);

            if (!mimeType.contains("image")) {
                throw new CustomException(ErrorCode.MISMATCH_FILE_MIMETYPE);
            }

            Image image = new Image();
            image.setUrl(s3Uploader.upload(file,categoryName));
            image.setProduct(product);
            imageRepository.save(image);
        }

        return ResponseEntity.ok("success");

    }

    @PostMapping("/products")
    public DetailProductDTO postProduct(@RequestBody ProductForm productForm)
    {
        Product product =  productService.addProduct(productForm);
        return DetailProductDTO.createProductDTO(product);
    }

    @GetMapping("/categories/parents")
    public List<Category> getParents() {
        return categoryService.findAllCategories();
    }



}
