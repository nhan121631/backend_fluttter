package com.edu.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.edu.dto.ProductDTO;
import com.edu.entity.ProductEntity;
import com.edu.repository.ProductRepository;
import com.edu.service.CategoryService;
import com.edu.service.ProductService;
import com.edu.util.MessageUtil;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageUtil messageUtil;

	@RequestMapping(value = "/quan-tri/product/list", method = RequestMethod.GET)
	 public String showList(Model m, @RequestParam("page") int page, 
								 @RequestParam("limit") int limit, HttpServletRequest request) {
		ProductDTO model = new ProductDTO();
		model.setPage(page);
		model.setLimit(limit);
		Pageable pageable = new PageRequest(page - 1, limit);
		model.setListResult(productService.findAll(pageable));
		model.setTotalItem(productService.getTotalItem());
		model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getLimit()));
		m.addAttribute("model", model);
		if(request.getParameter("message")!=null) {
			Map<String, String> message = messageUtil.getMessage(request.getParameter("message"));
			m.addAttribute("alert", message.get("alert"));
			m.addAttribute("message", message.get("message"));
		}
		return "admin/new/list";
	}
	
	@RequestMapping(value = "/quan-tri/product/viewadd", method = RequestMethod.GET)
	public String viewadd(Model m, HttpServletRequest request) {		
		ProductDTO model = new ProductDTO();

		if(request.getParameter("message")!=null) {
			Map<String, String> message = messageUtil.getMessage(request.getParameter("message"));
			m.addAttribute("alert", message.get("alert"));
			m.addAttribute("message", message.get("message"));
		}
		m.addAttribute("categories", categoryService.findAll());
		m.addAttribute("model",model);
		return "admin/new/add";
		
	}
	
	@RequestMapping(value = "/quan-tri/product/edit", method = RequestMethod.GET)
	public String editNew(Model m, @RequestParam(value = "id", required = false) Long id, HttpServletRequest request) {
		ProductDTO model = new ProductDTO();
		if(id != null) {
			model = productService.findById(id);
		}
		if(request.getParameter("message")!=null) {
			Map<String, String> message = messageUtil.getMessage(request.getParameter("message"));
			m.addAttribute("alert", message.get("alert"));
			m.addAttribute("message", message.get("message"));
		}
		m.addAttribute("categories", categoryService.findAll());
		m.addAttribute("model", model);
		return "admin/new/edit";
		
	}
	
	@RequestMapping(value="/quan-tri/product/add", method = RequestMethod.POST)
	public String add(Model model, 
			@RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
			@RequestParam(value = "name", required = false) String name, 
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "quantity", required = false) Integer quantity, 
			@RequestParam(value = "costPrice", required = false) Float costPrice, 
			@RequestParam(value = "sellPrice", required = false) Float sellPrice,
			@RequestParam(value = "categoryId", required = false) Long categoryId) throws UnsupportedEncodingException {

		// Kiểm tra các trường bắt buộc
		if (thumbnail == null || thumbnail.isEmpty() || 
		    name == null || name.trim().isEmpty() || 
		    description == null || description.trim().isEmpty() ||
		    quantity == null || quantity <= 0 || // Kiểm tra quantity
		    costPrice == null || costPrice <= 0 || // Kiểm tra cotsPrice
		    sellPrice == null || sellPrice <= 0 || // Kiểm tra sellPrice
		    categoryId == null) { // Kiểm tra categoryId

		    // Trả về lỗi nếu có trường thiếu hoặc không hợp lệ
		    return "redirect:viewadd?message=miss_value";
		}

	    
	    // Kiểm tra định dạng tệp tin
	    String fileName = thumbnail.getOriginalFilename();
	    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	    List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
	    
	    if (!validExtensions.contains(fileExtension)) {
	        return "redirect:viewadd?message=fail_photo";
	    }

	 // Chuyển đổi encoding cho 'name'
	    byte[] nameBytes = name.getBytes("ISO-8859-1");
	    String dname = new String(nameBytes, "UTF-8");

	    // Chuyển đổi encoding cho 'description'
	    byte[] descriptionBytes = description.getBytes("ISO-8859-1");
	    String ddescription = new String(descriptionBytes, "UTF-8");
	    
	    ProductDTO dto = new ProductDTO();
	    dto.setCategoryId(categoryId);
	    dto.setName(dname);
	    dto.setDescription(ddescription);
//	    dto.setId(newId);
	    dto.setQuantity(quantity);
	    dto.setCostPrice(costPrice);
	    dto.setSellPrice(sellPrice);
	    dto.setThumbnail(saveFile(thumbnail));
	    System.out.println(dto.toString());

	    System.out.println("====save=====");
	    
	    productService.save(dto);
	    return "redirect:list?page=1&limit=2&message=insert_success";
	}
	
	
	@RequestMapping(value="/quan-tri/product/update", method = RequestMethod.POST)
	public String update(Model model, 
			@RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
			@RequestParam(value = "name", required = false) String name, 
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "quantity", required = false) Integer quantity, 
			@RequestParam(value = "costPrice", required = false) Float costPrice, 
			@RequestParam(value = "sellPrice", required = false) Float sellPrice,
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			HttpServletRequest request) throws UnsupportedEncodingException {

		// Kiểm tra các trường bắt buộc
		if (
		    name == null || name.trim().isEmpty() || 
		    description == null || description.trim().isEmpty() ||
		    quantity == null || quantity <= 0 || // Kiểm tra quantity
		    costPrice == null || costPrice <= 0 || // Kiểm tra cotsPrice
		    sellPrice == null || sellPrice <= 0 || // Kiểm tra sellPrice
		    categoryId == null) { // Kiểm tra categoryId

		    // Trả về lỗi nếu có trường thiếu hoặc không hợp lệ
		    return "redirect:viewadd?message=miss_value";
		}

	    
;
	    


	 // Chuyển đổi encoding cho 'name'
	    byte[] nameBytes = name.getBytes("ISO-8859-1");
	    String dname = new String(nameBytes, "UTF-8");

	    // Chuyển đổi encoding cho 'description'
	    byte[] descriptionBytes = description.getBytes("ISO-8859-1");
	    String ddescription = new String(descriptionBytes, "UTF-8");
	    String id = request.getParameter("id");
	    ProductEntity productEntity = productRepository.findOne(Long.parseLong(id));

	    // Kiểm tra xem có ảnh mới hay không
	    if (thumbnail != null && !thumbnail.isEmpty()) {
		    // Kiểm tra định dạng tệp tin
		    String fileName = thumbnail.getOriginalFilename();
		    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		    List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
		    if (!validExtensions.contains(fileExtension)) {
		        return "redirect:viewadd?message=fail_photo";
		    }
	        // Xử lý ảnh mới (lưu ảnh mới)
	        // Ví dụ: productEntity.setThumbnail(uploadFile(thumbnail));
	        productEntity.setThumbnail(saveFile(thumbnail));
	    } else {
	        // Nếu không có ảnh mới, giữ lại ảnh cũ
	        productEntity.setThumbnail(productEntity.getThumbnail());
	    }
	    
	   
	    ProductDTO dto = new ProductDTO();
	    dto.setCategoryId(categoryId);
	    dto.setName(dname);
	    dto.setDescription(ddescription);
	    dto.setId(Long.parseLong(id));
	    dto.setQuantity(quantity);
	    dto.setCostPrice(costPrice);
	    dto.setSellPrice(sellPrice);
	    dto.setThumbnail(productEntity.getThumbnail());
	    System.out.println(dto.toString());

	    System.out.println("====save=====");
	    
	    productService.save(dto);
	    return "redirect:list?page=1&limit=2&message=update_success";
	}
	
	private String saveFile(MultipartFile file) {
		if(file != null && !file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String rootPath = System.getProperty("catalina.home");
				//tao forder save
				File dir = new File(rootPath+File.separator+"uploads");
				if(!dir.exists()) {
					dir.mkdir();
				}
				
				// creating the file on server
				String name = String.valueOf(new Date().getTime()+".jpg");
				File serverFile = new File(dir.getAbsoluteFile()+File.separator+name);
				//
				System.out.println("====== Path of image on server: "+serverFile.getPath());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				return name;
				
			} catch (IOException e) {
				System.out.println("======== Error upload file: "+e.getMessage());
				
			}
			
		}else {
			System.out.println("====== File not exist");
		}
		return null;
	}
	
}
