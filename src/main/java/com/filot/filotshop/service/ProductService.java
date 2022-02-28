package com.filot.filotshop.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.filot.filotshop.dto.product.ProductDTO;
import com.filot.filotshop.dto.product.ProductForm;
import com.filot.filotshop.entity.Category;
import com.filot.filotshop.entity.FileImage;
import com.filot.filotshop.entity.Product;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.repository.category.CategoryRepository;
import com.filot.filotshop.repository.product.FileImageRepository;
import com.filot.filotshop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final FileImageRepository fileImageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public final static int SHOW_PRODUCT_COUNT = 4;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷





    @Transactional
    public Product addProduct( ProductForm productForm) {
        Product product = Product.createProduct(productForm);
        Category category = categoryRepository.findByName(productForm.getCategoryName())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setCategory(category);

        product = productRepository.save(product);
        return product;
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

    public static String uploadFolder = "/Users/wonjongseo/Downloads/Filot-Shop/src/main/resources/static/img";
    @Transactional
    public void saveProductImage(MultipartFile[] uploadFile,Long productId) {
        Product product = productRepository.getById(productId);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        String str = sdf.format(date);

        String datePath = str.replace("-", File.separator);

        File uploadPath = new File(uploadFolder, datePath);

        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile) {

            FileImage fileImage = new FileImage();
            String uploadFileName = multipartFile.getOriginalFilename();
            fileImage.setFileName(uploadFileName);
            fileImage.setUploadPath(datePath);
            fileImage.setProduct(product);
            String uuid = UUID.randomUUID().toString();
            fileImage.setUuid(uuid);


            fileImageRepository.save(fileImage);

            uploadFileName = uuid + "_" + uploadFileName;

            File saveFile = new File(uploadPath, uploadFileName);
            String uploadImageUrl = putS3(uploadPath, uploadFileName);
            removeNewFile(uploadPath);
        }
    }
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

}
