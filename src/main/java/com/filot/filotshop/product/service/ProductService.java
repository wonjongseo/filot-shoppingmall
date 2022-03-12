package com.filot.filotshop.product.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.ProductForm;
import com.filot.filotshop.category.entity.Category;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.category.repository.CategoryRepository;
import com.filot.filotshop.product.repository.ProductRepository;
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
    return  null;

    }
    @Transactional
    public Product addProduct(ProductForm productForm, String url) {
        Product product = Product.createProduct(productForm,url);

        Category category = categoryRepository.findByName(productForm.getCategoryName())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setCategory(category);

        return  productRepository.save(product);

    }

    public List<Product> findAllProductsForAdmin(){
        return productRepository.findAll();
    }

    public List<ProductDTO> findAllProducts(){
        return productRepository.findAllProductDTO();
    }

    @Transactional
    public int changeProductAmount(Product product, int amount) {
        product.changeAmount(amount);
        return product.getAmount();
    }

    public Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.INVALID_REQUEST_SORT));
    }



}
