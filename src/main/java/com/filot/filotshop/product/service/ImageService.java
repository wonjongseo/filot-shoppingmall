package com.filot.filotshop.product.service;

import com.filot.filotshop.config.s3.S3Service;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.product.entity.Image;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.product.repository.ImageRepository;
import com.filot.filotshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ProductRepository productRepository;
    private final S3Service s3Uploader;
    private final ImageRepository imageRepository;

    private Product findByIdOrThrowError(Long productId){
        return  productRepository.findById(productId)
                .orElseThrow(()->new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    //

    @Transactional
    public Product deleteImage(Long productId, String  fileName) {

        Product product = findByIdOrThrowError(productId);
        Image removedImage = null;
        for (Image image : product.getImages()) {
            if(image.getName().equals(fileName))
                removedImage = image;
        }

        if(removedImage !=null){
            s3Uploader.delete(removedImage.getName());
            imageRepository.delete(removedImage);
        }

        return product;
    }
    @Transactional
    public Product addImage(Long productId, MultipartFile file) {

        Product product = findByIdOrThrowError(productId);

        Image newImage = new Image();

        newImage.setUrl(s3Uploader.upload(file, product.getCategory().getName()));

        newImage.setName(product.getCategory().getName() + "/" + file.getOriginalFilename());
        newImage.setProduct(product);
        imageRepository.save(newImage);

        return product;
    }
}
