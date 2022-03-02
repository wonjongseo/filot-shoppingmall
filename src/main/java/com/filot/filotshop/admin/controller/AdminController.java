package com.filot.filotshop.admin.controller;

import com.filot.filotshop.commons.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
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

    @PostMapping("/aa")
    public RequestEntity<String> postBannerUrl(MultipartFile bannerFile) {
        return null;
    }
}
