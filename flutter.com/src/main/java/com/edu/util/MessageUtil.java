package com.edu.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
	
	public Map<String, String> getMessage(String message){
		Map<String, String> result = new HashMap<>();
		if(message.equals("update_success")) {
			result.put("message", "Update Success");
			result.put("alert", "success");
		}else if (message.equals("insert_success")) {
			result.put("alert", "success");
			result.put("message", "Insert Success");
		}else if(message.equals("error_system")) {
			result.put("alert", "danger");
			result.put("message", "Error System");
		}else if(message.equals("delete_success")) {
			result.put("alert", "success");
			result.put("message", "Delete Success");
		}else if(message.equals("miss_value")) {
			result.put("alert", "danger");
			result.put("message", "Miss value");
		}else if(message.equals("review_success")) {
			result.put("alert", "success");
			result.put("message", "Review Success");
		}
		return result;
	}
}
