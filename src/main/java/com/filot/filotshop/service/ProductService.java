package com.filot.filotshop.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.dto.product.ProductForm;
import com.filot.filotshop.entity.Category;
import com.filot.filotshop.entity.Product;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.repository.category.CategoryRepository;
import com.filot.filotshop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public final static int SHOW_PRODUCT_COUNT = 4;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷


    @Transactional
    public Product addProduct(ProductForm productForm) {
        Product product = Product.createProduct(productForm);
        Category category = categoryRepository.findByName(productForm.getCategoryName())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setCategory(category);

        return   productRepository.save(product);


    }

    public List<Product> findAllProductsForAdmin(){
        return productRepository.findAll();
    }

    public List<ProductDTO> findAllProducts(){
        return productRepository.findAllToDTO();
    }


    public Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.INVALID_REQUEST_SORT));
    }



}
