package com.edu.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.dto.CategoryDTO;
import com.edu.service.CategoryService;
import com.edu.util.MessageUtil;

@Controller(value = "categoryOfController")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageUtil messageUtil;
	
	@RequestMapping(value = "/quan-tri/category/list", method = RequestMethod.GET)
	 public String showList(Model m, @RequestParam("page") int page, 
								 @RequestParam("limit") int limit, HttpServletRequest request) {
		CategoryDTO model = new CategoryDTO();
		model.setPage(page);
		model.setLimit(limit);
		Pageable pageable = new PageRequest(page - 1, limit);
		model.setListResult(categoryService.getAll(pageable));
		model.setTotalItem(categoryService.getTotalItem());
		model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getLimit()));
		m.addAttribute("model", model);
		if(request.getParameter("message")!=null) {
			Map<String, String> message = messageUtil.getMessage(request.getParameter("message"));
			m.addAttribute("alert", message.get("alert"));
			m.addAttribute("message", message.get("message"));
		}
		return "admin/category/list";
	}
	
	@RequestMapping(value = "/quan-tri/category/edit", method = RequestMethod.GET)
	public String editNew(Model m, @RequestParam(value = "id", required = false) Long id, HttpServletRequest request) {
		CategoryDTO model = new CategoryDTO();
		if(id != null) {
			model = categoryService.findById(id);
		}
		if(request.getParameter("message")!=null) {
			Map<String, String> message = messageUtil.getMessage(request.getParameter("message"));
			m.addAttribute("alert", message.get("alert"));
			m.addAttribute("message", message.get("message"));
		}
		m.addAttribute("model", model);
		return "admin/category/edit";
		
	}
	
//	@RequestMapping(value = "/quan-tri/category/viewadd", method = RequestMethod.GET)
//	public String viewadd(Model m, @RequestParam(value = "id", required = false) Long id, HttpServletRequest request) {
//		CategoryDTO model = new CategoryDTO();
//		if(id != null) {
//			model = categoryService.findById(id);
//		}
//		if(request.getParameter("message")!=null) {
//			Map<String, String> message = messageUtil.getMessage(request.getParameter("message"));
//			m.addAttribute("alert", message.get("alert"));
//			m.addAttribute("message", message.get("message"));
//		}
//		m.addAttribute("model", model);
//		return "admin/category/add";
//		
//	}
//	
	
//	@RequestMapping(value = "/quan-tri/category/add", method = RequestMethod.POST)
//	public String add(Model m, @ModelAttribute("model") CategoryDTO dto) {
//
//		System.out.println(dto.getName()+" "+dto.getCode());
//		if(dto.getName().equals("") || dto.getCode().equals("")) {
//			return "redirect:list?page=1&limit=2&message=error_system";
//		}
//		categoryService.save(dto);
//		return "redirect:list?page=1&limit=2&message=insert_success";	
//	}
	
//	@RequestMapping(value = "/quan-tri/category/update", method = RequestMethod.POST)
//	public String update(Model m, @ModelAttribute("model") CategoryDTO dto) {
//
//		System.out.println(dto.getName()+" "+dto.getCode());
//		if(dto.getName().equals("") || dto.getCode().equals("")) {
//			return "redirect:list?page=1&limit=2&message=error_system";
//		}
//		categoryService.update(dto);
//		return "redirect:list?page=1&limit=2&message=update_success";	
//	}
//	

}

