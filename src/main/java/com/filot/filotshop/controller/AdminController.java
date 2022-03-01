package com.filot.filotshop.controller;

import com.filot.filotshop.commons.S3Uploader;
import com.filot.filotshop.dto.UserDTO;
import com.filot.filotshop.dto.product.DetailProductDTO;
import com.filot.filotshop.dto.product.ProductDTO;
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
            checkMimeType(file);
            Image image = new Image();

            image.setUrl(s3Uploader.upload(file,categoryName));
            image.setProduct(product);
            imageRepository.save(image);
        }

        return ResponseEntity.ok("success");

    }

    private boolean checkMimeType(MultipartFile file) {
        File checkFile = new File(file.getOriginalFilename());

        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

        String mimeType = mimetypesFileTypeMap.getContentType(checkFile);

        if (!mimeType.contains("image")) {
            throw new CustomException(ErrorCode.MISMATCH_FILE_MIMETYPE);
        }
        return true;

    }


    @PostMapping("/products")
    public ResponseEntity<ProductDTO> postProduct( ProductForm productForm, MultipartFile file) {
        System.out.println("productForm = " + productForm);
        System.out.println
                ("file = " + file.getOriginalFilename());
        checkMimeType(file);
        String url = s3Uploader.upload(file, productForm.getCategoryName());
        System.out.println("url = " + url);
        Product product = productService.addProduct(productForm,url);


        return ResponseEntity.ok(ProductDTO.createProductDTO(product));



    }

    @GetMapping("/categories/parents")
    public List<Category> getParents() {
        return categoryService.findAllCategories();
    }



}
