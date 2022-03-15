package com.filot.filotshop.admin.controller;

import com.filot.filotshop.config.s3.S3Service;
import com.filot.filotshop.config.LocalUploader;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.ProductForm;
import com.filot.filotshop.product.entity.Image;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.product.repository.ImageRepository;
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
    private final ImageRepository imageRepository;
    private final LocalUploader localUploader;



    @PostMapping("/image")
    public ResponseEntity<String> uploads(
            @RequestParam("product_id") Product product ,
            @RequestParam("category_name") String categoryName,
            @RequestHeader(value="Host") String host,
            MultipartFile[] files)  {

        for (MultipartFile file : files) {
            checkMimeType(file);
            Image image = new Image();

            if(host.equals("localhost:8080")){
                image.setUrl(localUploader.saveImageInLocalMemory(file));
            }else{
                image.setUrl(s3Uploader.upload(file,categoryName));
            }

            image.setProduct(product);
            imageRepository.save(image);
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<Integer> changeProductAmount(
            @PathVariable(name = "product_id") Product product,
            @RequestParam(name = "amount") int amount) {
        int changedAmount = productService.changeProductAmount(product, amount);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(changedAmount);
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

    @PostMapping("/")
    public ResponseEntity<ProductDTO> postProduct(ProductForm productForm, MultipartFile file,@RequestHeader(value="Host") String host) {
        System.out.println("productForm = " + productForm);
        System.out.println("file = " + file.getOriginalFilename());


        checkMimeType(file);

        String url = "";
        if(host.equals("localhost:8080")){
            url = localUploader.saveImageInLocalMemory(file);
        }else{
            url = s3Uploader.upload(file, productForm.getCategoryName());
        }
        Product product = productService.addProduct(productForm,url);
        return ResponseEntity.ok(ProductDTO.createProductDTO(product));
    }

}
