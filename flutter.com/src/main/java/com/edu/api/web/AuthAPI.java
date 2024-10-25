package com.edu.api.web;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.entity.UserEntity;
import com.edu.repository.UserRepository;
import com.edu.service.CustomUserDetailsService;
import com.edu.service.EmailService;
import com.edu.service.UserService;


@RestController(value = "authAPIOfWeb")

public class AuthAPI {
	/*
	 * @Autowired private AuthenticationManager authenticationManager;
	 */
	 @Autowired
	    private UserService userService;
    
    @GetMapping("/api/data")
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok("Hello from Spring MVC");
    }
    

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired 
    private UserRepository userRepository;

    
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {
        Map<String, Object> response = new HashMap<>();
        
        System.out.println(password);
        try {
            // Lấy thông tin người dùng từ dịch vụ
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Kiểm tra xem người dùng có tồn tại và mật khẩu có khớp không
            if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
                response.put("success", true);
                
                response.put("user", userRepository.findUserByUname(username));
                response.put("message", "Đăng nhập thành công");
            } else {
                response.put("success", false);
                response.put("message", "Tên đăng nhập hoặc mật khẩu không chính xác");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Đã xảy ra lỗi: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }



	/*
	 * @PostMapping("/api/register") public ResponseEntity<Map<String, Object>>
	 * register(@RequestBody UserEntity user) { Map<String, Object> response = new
	 * HashMap<>(); System.out.println(user.getPassword()+" "+ user.getUserName());
	 * if (userService.userExists(user.getUserName())) { response.put("success",
	 * false); response.put("message", "Tên người dùng đã tồn tại");
	 * 
	 * return ResponseEntity.ok(response); }
	 * 
	 * if (userService.emailExists(user.getEmail())) { response.put("success",
	 * false); response.put("message", "Email đã tồn tại");
	 * 
	 * return ResponseEntity.ok(response); }
	 * 
	 * userService.save(user);
	 * 
	 * response.put("success", true); response.put("message", "Đăng ký thành công");
	 * return ResponseEntity.ok(response); }
	 */
    
    @PostMapping("/api/register")
    public ResponseEntity<Map<String, Object>> register(
        @RequestParam("userName") String userName,
        @RequestParam("password") String password,
        @RequestParam("fullName") String fullName,
        @RequestParam("email") String email) {

        Map<String, Object> response = new HashMap<>();
        System.out.println(password + " " + userName);

        if (userService.userExists(userName)) {
            response.put("success", false);
            response.put("message", "Tên người dùng đã tồn tại");
            return ResponseEntity.ok(response);
        }

        if (userService.emailExists(email)) {
            response.put("success", false);
            response.put("message", "Email đã tồn tại");
            return ResponseEntity.ok(response);
        }

        // Tạo UserEntity từ các tham số
        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);

        userService.save(user);

        response.put("success", true);
        response.put("message", "Đăng ký thành công");
        return ResponseEntity.ok(response);
    }

    
    public String generateResetCode() {
        Random random = new Random();
        // Tạo số ngẫu nhiên từ 100000 đến 999999
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
    
    @Autowired
    private EmailService emailService; // Lớp dịch vụ gửi email

    @PostMapping("/api/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        // Kiểm tra xem email có tồn tại không
        if (!userService.emailExists(email)) {
            response.put("success", false);
            response.put("message", "Email không tồn tại");
            return ResponseEntity.ok(response);
        }

        // Tạo mã đặt lại mật khẩu
//        String resetCode = UUID.randomUUID().toString();
        String resetCode = generateResetCode();
        System.out.println("Mã reset: " + resetCode);
        userService.saveResetPasswordCode(email, resetCode); // Lưu mã vào cơ sở dữ liệu
        System.out.println(resetCode);
        
        // Gửi mã qua email
        emailService.sendPasswordResetCode(email, resetCode);
        
        response.put("success", true);
        response.put("message", "Mã đặt lại mật khẩu đã được gửi đến email của bạn");
        return ResponseEntity.ok(response);
    }
    
    
    @PostMapping("/api/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("resetCode") String resetCode,
            @RequestParam("newPassword") String newPassword) {

        Map<String, Object> response = new HashMap<>();

        // Kiểm tra xem email có tồn tại không
        if (!userService.emailExists(email)) {
            response.put("success", false);
            response.put("message", "Email không tồn tại");
            return ResponseEntity.ok(response);
        }

        // Lấy thông tin người dùng từ email
        UserEntity user = userService.findByEmail(email);
        
        // Kiểm tra mã xác thực
        if (!resetCode.equals(user.getResetPasswordCode())) {
            response.put("success", false);
            response.put("message", "Mã xác thực không đúng");
            return ResponseEntity.ok(response);
        }

        // Kiểm tra thời gian mã xác nhận
        LocalDateTime creationTime = user.getResetPasswordCodeCreationTime();
        if (creationTime != null && Duration.between(creationTime, LocalDateTime.now()).toMinutes() > 5) {
            response.put("success", false);
            response.put("message", "Mã xác nhận đã hết hạn");
            return ResponseEntity.ok(response);
        }

        // Cập nhật mật khẩu mới
        user.setPassword(newPassword); // Mã hóa mật khẩu
        userService.save(user);
        System.out.println("newpass:"+newPassword);
        response.put("success", true);
        response.put("message", "Đổi mật khẩu thành công");
        return ResponseEntity.ok(response);
    }


    
}
