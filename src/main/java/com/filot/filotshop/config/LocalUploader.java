package com.filot.filotshop.config;


import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class LocalUploader {
    private final String UPLOAD_FOLDER = "/Users/wonjongseo/aStudy/Filot-Shop/src/main/resources/static/img";

    public String saveImageInLocalMemory(MultipartFile file){
        String uploadFileName = file.getOriginalFilename();
        File saveFile = new File(UPLOAD_FOLDER, uploadFileName);
        try {
            file.transferTo(saveFile);
            return saveFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CustomException(ErrorCode.FAIL_UPLOAD_IMAGE);
    }
    public String saveImageInLocalMemory(MultipartFile file, String fileName){
        String uploadFileName = fileName;
        File saveFile = new File(UPLOAD_FOLDER, uploadFileName);
        try {
            file.transferTo(saveFile);
            return saveFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CustomException(ErrorCode.FAIL_UPLOAD_IMAGE);
    }

}
