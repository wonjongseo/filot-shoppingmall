package com.filot.filotshop.config.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public  String getFileName(String url) {
        System.out.println("url = " + url);
        int lastIndexOf = url.lastIndexOf("com/");
        return url.substring(lastIndexOf + 4);
    }

    public String delete(String fileName) {
        System.out.println("fileName = " + fileName);

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
            amazonS3Client.deleteObject(deleteObjectRequest);
        return "success";
    }

    public String upload(MultipartFile multipartFile, String dirName) {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile, dirName);
    }


    public String uploadBanner( MultipartFile multipartFile ) {

        String oriName = "banner/banner.jpg";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(Mimetypes.getInstance().getMimetype(multipartFile.getOriginalFilename()));


        byte[] bytes ;

        ByteArrayInputStream byteArrayInputStream = null;
        try{
            bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            objectMetadata.setContentLength(bytes.length);
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, oriName, byteArrayInputStream, objectMetadata);

            amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return amazonS3Client.getUrl(bucket, oriName).toString();
    }


    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/"  +uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        System.out.println("uploadImageUrl = " + uploadImageUrl);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return getUrl(fileName);
    }

    public String getUrl(String fileName) {
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file)  {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        try {
            if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
                try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INVALID_REQUEST_IMAGE);
        }

        return Optional.empty();
    }


}