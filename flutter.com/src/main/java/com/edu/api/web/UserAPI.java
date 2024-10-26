package com.edu.api.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.entity.UserEntity;
import com.edu.service.UserService;

@RestController(value = "userAPIOfWeb")
public class UserAPI {
	@Autowired UserService userService;
	
	@PutMapping(value = "/api/user")
	public ResponseEntity<Map<String, Object>>  UpdateUser(@RequestParam("id") Long id,
			@RequestParam ("fullName") String fullName, @RequestParam ("email") String email) {
        Map<String, Object> response = new HashMap<>();

		if (userService.emailExists(email) && (id!= userService.findByEmail(email).getId())){
            response.put("success", false);
            response.put("message", "Email của bạn đã tồn tại");
            System.out.println("fal");
            return ResponseEntity.ok(response);
        }
		
		UserEntity userNew = new UserEntity();
		  userNew = userService.findOneById(id);
		  userNew.setFullName(fullName);
		  userNew.setEmail(email);
	      userService.update(userNew);
	      System.out.println("success");
	        response.put("success", true);
	        response.put("message", "Đăng ký thành công");
		
		return ResponseEntity.ok(response);
	}
}
