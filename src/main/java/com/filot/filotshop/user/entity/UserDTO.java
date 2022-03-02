package com.filot.filotshop.user.entity;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class UserDTO {

    public static UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRoles(user.getRoles());
        userDTO.setDetailAddress(user.getAddress().getDetailAddress());
        userDTO.setRoadAddress(user.getAddress().getRoadAddress());
        userDTO.setPoint(user.getPoint());

        return userDTO;
    }


    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private List<String> roles = new ArrayList<>();
    private String detailAddress;
    private String roadAddress;
    private int point;


}
