package com.filot.filotshop.product.service;

import com.filot.filotshop.config.s3.S3Service;
import com.filot.filotshop.product.entity.Image;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.ProductForm;
import com.filot.filotshop.category.entity.Category;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.category.repository.CategoryRepository;
import com.filot.filotshop.product.repository.ImageRepository;
import com.filot.filotshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final S3Service s3Uploader;
    private final ImageRepository imageRepository;

    public final static int SHOW_PRODUCT_COUNT = 4;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private Product findByIdOrThrowError(Long productId){
        return  productRepository.findById(productId)
                .orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public Product addProduct(ProductForm productForm, String url) {
        Product product = Product.createProduct(productForm,url);

        Category category = categoryRepository.findByName(productForm.getCategoryName())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setCategory(category);

        return  productRepository.save(product);
    }



    @Transactional
    public int changeProductAmount(Product product, int amount) {
        product.changeAmount(amount);
        return product.getAmount();
    }

    public Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.INVALID_REQUEST_SORT));
    }



    // 메인이미지 변경
    @Transactional
    public Product changeMainImage(Long productId, MultipartFile file, String host) {
        Product product = findByIdOrThrowError(productId);

        int i = product.getImageUrl().lastIndexOf("com/");
        String mainImageUrl = product.getImageUrl().substring(i+4);
        s3Uploader.delete(mainImageUrl);

        String newMainImageUrl = s3Uploader.upload(file, product.getCategory().getName());
        product.setImageUrl(newMainImageUrl);

        return product;
    }


    // 디테일 이미지들 추가
    @Transactional
    public void addDetailImages(MultipartFile file, Product product) {
        Image image = new Image();

        image.setUrl(s3Uploader.upload(file,product.getCategory().getName()));
        image.setName(product.getCategory().getName() + "/" + file.getOriginalFilename());
        image.setProduct(product);
        imageRepository.save(image);

    }


    public List<Product> findAllProductsForAdmin(){
        return productRepository.findAll();
    }
    public List<ProductDTO> findAllProducts(){
        return productRepository.findAllProductDTO();
    }



    // 상품 제거

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findByIdOrThrowError(productId);

        int lastIndexOf = product.getImageUrl().lastIndexOf(".com");
        String deletedFileName = product.getImageUrl().substring(lastIndexOf + 5);

        s3Uploader.delete(deletedFileName);
        List<Image> images = product.getImages();
        for (Image image : images) {
            s3Uploader.delete(image.getName());
            imageRepository.delete(image);
        }

        productRepository.delete(product);

    }




}
