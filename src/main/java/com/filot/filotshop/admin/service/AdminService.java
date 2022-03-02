package com.filot.filotshop.admin.service;

import com.filot.filotshop.user.entity.User;
import com.filot.filotshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final UserRepository userRepository;

    public List<User> findUserAll(){
        return userRepository.findAll();
    }


    @Transactional
    public boolean updateUserRole(User user, String role) {
        String ROLE = "ROLE_" + role.toUpperCase();
        List<String> roles = user.getRoles();
        if (roles.contains(ROLE)) {
            return false;
        }
        roles.add(ROLE);
        return true;
    }

    @Transactional
    public Boolean deleteUser(User user) {
        userRepository.delete(user);
        return true;
    }

    @Transactional
    public void restoreUser(Long userId) {

    }
}
