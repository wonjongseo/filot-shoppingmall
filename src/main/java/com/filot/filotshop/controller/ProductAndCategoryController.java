package com.filot.filotshop.controller;


import com.filot.filotshop.dto.category.CategoryDTO;
import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.entity.Category;
import com.filot.filotshop.entity.FileImage;
import com.filot.filotshop.entity.Product;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.repository.product.FileImageRepository;
import com.filot.filotshop.repository.product.ProductRepository;
import com.filot.filotshop.service.CategoryService;
import com.filot.filotshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ProductAndCategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final FileImageRepository fileImageRepository;
    // ok
    @GetMapping(value = "/product-list/{category_name}")
    public List<ProductDTO> showProductByCategoryName(
            @PathVariable(value = "category_name") String name,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "sort", required = false) String sort) {

        if (page == null && sort == null) {
            // 따로 정렬 쿼리 없으면
            if (name.equals("main")) {
                // 모든 상품 조회
                return productService.findAllProducts();
            } else {
                // 카테고리 이름에 맞는 상품 조회
                return categoryService.findProductByName(name);
            }
        } else {
            return categoryService.findProductByName(name, page, sort);
        }
    }


    // ok
    @GetMapping(value = "/category-list/{category_id}")
    public List<CategoryDTO> showCategoryList(@PathVariable(value = "category_id") String name) {

        if (name.equals("main")) return categoryService.findAllMainCategories();

        Category category = categoryService.findCategoryByName(name).
                orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));

        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for (Category child : category.getChild()) {
            CategoryDTO categoryDTO = new CategoryDTO(child.getId(), child.getName());
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    // ok

    @GetMapping(value = "/product/{product_id}")
    public Product showProductById(@PathVariable(name = "product_id") Long id) {
        System.out.println("id = " + id);
        return productService.findProductById(id);
    }


    @GetMapping("/products/{product_id}/image")
    public ResponseEntity<byte[]> getImages(@PathVariable(name = "product_id") Long productId) {
        Product product = productRepository.getById(productId);
        String uploadFolder = "/Users/wonjongseo/Downloads/Filot-Shop/src/main/resources/static/img/";
        List<FileImage> imageUrls = product.getImageUrls();
        for (FileImage imageUrl : imageUrls) {
            StringBuilder fileName = new StringBuilder();
            fileName.append(imageUrl.getUploadPath() + File.separator + imageUrl.getUuid() + File.separator + imageUrl.getFileName());
            File file = new File(uploadFolder + fileName);

            System.out.println("file = " + file);
        }
        return  null;
    }
    @GetMapping("/product/image")
    public ResponseEntity<byte[]> getImage(String fileName) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

        String uploadFolder = "/Users/wonjongseo/Downloads/Filot-Shop/src/main/resources/static/img/";
        File file = new File(uploadFolder + fileName);
        ResponseEntity<byte[]> response = null;

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", mimeTypesMap.getContentType(file));
            response = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
