package com.filot.filotshop.user.entity;

import lombok.Data;

@Data
public class UpdateDTO {
    private String email;
    private String newPassword;
    private String code;
}
