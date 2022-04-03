package com.filot.filotshop.admin.controller;

import com.filot.filotshop.config.s3.S3Service;
import com.filot.filotshop.product.entity.*;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.product.service.ImageService;
import com.filot.filotshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;


@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;
    private final S3Service s3Uploader;
    private final ImageService imageService;

    // Product


    // 상품 등록
    @PostMapping("/")
    public ResponseEntity<ProductDTO> postProduct(ProductForm productForm, MultipartFile file) {

//        checkMimeType(file);
        String url =s3Uploader.upload(file, productForm.getCategoryName());

        Product product = productService.addProduct(productForm,url);
        return ResponseEntity.ok(ProductDTO.createProductDTO(product));
    }

    //상품 삭제
    @DeleteMapping("/{product_id}")
    public Long deleteProduct(@PathVariable Long product_id) {
        productService.deleteProduct(product_id);
        return product_id;
    }



    // 메인 이미지 추가
    @PutMapping("/{product_id}/image")
    public DetailProductDTO changeMainImage(@PathVariable("product_id") Long productId,  MultipartFile file,@RequestHeader(name = "Host") String host) {
        Product product = productService.changeMainImage(productId, file,host);

        return DetailProductDTO.createDetailProductDTO(product);
    }

    // 상품 수량ㅂ 변경
    @PutMapping("/{product_id}")
    public ResponseEntity<Integer> changeProductAmount(
            @PathVariable(name = "product_id") Product product,
            @RequestParam(name = "amount") int amount) {
        int changedAmount = productService.changeProductAmount(product, amount);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(changedAmount);
    }


    // Image

    // 디테일 이미지 추가
    @PostMapping("/{product_id}/image")
    public DetailProductDTO addImageInProduct(@PathVariable("product_id") Long productId,  MultipartFile file) {
        Product product = imageService.addImage(productId, file);

        return DetailProductDTO.createDetailProductDTO(product);
    }



    // 디테일 이미지 제거
    @DeleteMapping("/{product_id}/image")
    public DetailProductDTO deleteImageInProduct(@PathVariable("product_id") Long productId, String fileName) {
        Product product = imageService.deleteImage(productId, fileName);
        return DetailProductDTO.createDetailProductDTO(product);
    }

    // 디테일 이미지 여러장  등록
    @PostMapping("/image")
    public ResponseEntity<String> uploads(
            @RequestParam("product_id") Product product ,

            MultipartFile[] files)  {


        for (MultipartFile file : files) {
//            checkMimeType(file);
            productService.addDetailImages(file, product);
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




}
