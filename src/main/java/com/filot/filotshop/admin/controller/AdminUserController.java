package com.filot.filotshop.admin.controller;

import com.filot.filotshop.admin.service.AdminService;
import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.user.entity.UserDTO;
import com.filot.filotshop.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;
    private final AdminService adminService;
    // 모든 유저 조회

    @GetMapping("/all")
    public List<UserDTO> findUserAll(){
        List<UserDTO> userDTOs = new ArrayList<>();
        userRepository.findAll().stream()
                .forEach(user -> userDTOs.add(UserDTO.createUserDTO(user)));
        return userDTOs;
    }

    // 개별 유저 조회
    @GetMapping("/details/{user_id}")
    public ResponseEntity<UserDTO> findUser(@PathVariable(value = "user_id") User user) {
        return ResponseEntity.ok(UserDTO.createUserDTO(user));
    }

    // 유저 ROLE 변경
    @PostMapping("/roles/{user_id}")
    public ResponseEntity<Boolean> updateUserRole(@PathVariable(value = "user_id") User user ,
                                                  @RequestBody Map<String , String> role){
        boolean ok = adminService.updateUserRole(user, role.get("role"));

        return ResponseEntity.ok(ok);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable(value = "user_id") User user) {
        return ResponseEntity.ok(adminService.deleteUser(user));
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<UserDTO> restoreUser
            (@PathVariable(value = "user_id") Long userId) {
        adminService.restoreUser(userId);
        return null;
    }


}
