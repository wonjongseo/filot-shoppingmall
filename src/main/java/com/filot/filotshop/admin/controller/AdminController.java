package com.filot.filotshop.admin.controller;

import com.filot.filotshop.commons.service.S3Service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final S3Service s3Service;

    @PostMapping("/banners")
    public ResponseEntity<String> postBannerUrl(MultipartFile bannerFile) {
        String bannerUrl=  s3Service.upload(bannerFile,"banner");
        return ResponseEntity.ok(bannerUrl);
    }

    @GetMapping("/banners")
    public ResponseEntity<String> getBannerUrl(@RequestParam(required = false) String bannerName) {
        if (bannerName == null) {
            return ResponseEntity.ok(s3Service.getUrl("banner/banner.jpg"));
        }
        String bannerUrl = "banner/" + bannerName + ".jpg";
        return ResponseEntity.ok(s3Service.getUrl(bannerUrl));
    }
}
