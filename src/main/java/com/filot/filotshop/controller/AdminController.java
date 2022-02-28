package com.filot.filotshop.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.filot.filotshop.dto.UserDTO;
import com.filot.filotshop.entity.FileImage;
import com.filot.filotshop.dto.category.CategoryForm;
import com.filot.filotshop.dto.product.ProductForm;
import com.filot.filotshop.entity.Category;
import com.filot.filotshop.entity.Product;
import com.filot.filotshop.entity.User;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.repository.UserRepository;
import com.filot.filotshop.repository.product.ProductRepository;
import com.filot.filotshop.service.AdminService;
import com.filot.filotshop.service.CategoryService;
import com.filot.filotshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final AmazonS3Client amazonS3Client;


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

    @PostMapping("/products")
    public Product postProduct(@RequestBody ProductForm productForm) {
        return productService.addProduct(productForm);
    }

    @GetMapping("/categories/parents")
    public List<Category> getParents() {
        return categoryService.findAllCategories();
    }

    @PostMapping(value = "/products/images")
    public void uploadProductImage(@RequestParam("id") Long productId, MultipartFile[] uploadFile) {

        for (MultipartFile multipartFile : uploadFile) {
            File checkFile = new File(multipartFile.getOriginalFilename());

            MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

            String mimeType = mimetypesFileTypeMap.getContentType(checkFile);

            if (!mimeType.contains("image")) {
                throw new CustomException(ErrorCode.MISMATCH_FILE_MIMETYPE);
            }
        }
        productService.saveProductImage(uploadFile, productId);

      return;
    }

}
