package com.filot.filotshop.admin.controller;

import com.filot.filotshop.config.s3.S3Service;
import com.filot.filotshop.config.LocalUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final S3Service s3Service;
    private final LocalUploader localUploader;

    @PostMapping("/banners")
    public ResponseEntity<String> postBannerUrl(MultipartFile bannerFile, HttpServletRequest request) {
        String host = request.getHeader("host");
        String banner = "";

        String bannerName = "banner/banner.jpg";

        if (host.equals("localhost:8080")) {
            localUploader.saveImageInLocalMemory(bannerFile,bannerName);
            return ResponseEntity.ok(banner);
        }

        banner= s3Service.uploadBanner(bannerFile);
        return ResponseEntity.ok(banner);
    }


    @GetMapping("/banners")
    public ResponseEntity<String> getBannerUrl() {
        return ResponseEntity.ok(s3Service.getUrl("banner/banner.jpg"));
    }
}
