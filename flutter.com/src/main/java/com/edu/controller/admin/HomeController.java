package com.edu.controller.admin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "homeControllerOfAdmin")

public class HomeController {
	@RequestMapping(value = "/dang-nhap", method = RequestMethod.GET)
	public String loginPage(Model model) {
		return "login";
	}
	
	@RequestMapping(value = "quan-tri/home", method = RequestMethod.GET)
	public String home(Model model) {
		return "admin/home";
	}
	@RequestMapping(value = "/thoat", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
	}
		return "redirect:/dang-nhap";
}
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied(Model model) {
		return "redirect:/dang-nhap?accessDenied";
	}
}
