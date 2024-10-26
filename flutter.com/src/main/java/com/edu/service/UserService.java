package com.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.edu.entity.RoleEntity;
import com.edu.entity.UserEntity;
import com.edu.repository.RoleRepository;
import com.edu.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean userExists(String username) {
        return userRepository.findByUserName(username).isPresent();
    }

    public void save(UserEntity user) {
        System.out.println("Username: " + user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);  
        RoleEntity userRole = roleRepository.findByCode("USER");
        user.setRoles(Collections.singletonList(userRole));

        userRepository.save(user);  // Lưu người dùng vào cơ sở dữ liệu
    }
    
    public UserEntity findOneById(Long id) {
    	return userRepository.findOne(id);
    }
    
    public void update(UserEntity user) {
        System.out.println("Username: " + user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    //    user.setStatus(1);  
     //   RoleEntity userRole = roleRepository.findByCode("USER");
      //  user.setRoles(Collections.singletonList(userRole));

        userRepository.save(user);  // Lưu người dùng vào cơ sở dữ liệu
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveResetPasswordCode(String email, String resetCode) {
        UserEntity user = userRepository.findByEmail(email);
        user.setResetPasswordCode(resetCode);
        user.setResetPasswordCodeCreationTime(LocalDateTime.now());
        userRepository.save(user);
    }
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
