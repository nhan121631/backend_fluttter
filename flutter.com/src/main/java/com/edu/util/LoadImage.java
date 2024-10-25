package com.edu.util;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoadImage
 */
@WebServlet(urlPatterns = "/image/*")
public class LoadImage extends HttpServlet {    
	private String imagePath;
   
	
	@Override
	public void init() throws ServletException {
		imagePath = System.getProperty("catalina.home")+File.separator+"uploads";		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get image by pathInfo
		String requestdImage = request.getPathInfo();
		
		if(requestdImage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// Decode the file
		
		File image = new File(imagePath, URLDecoder.decode(requestdImage,"UTF-8"));
		
		if(!image.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		//Get content type by file name
		String contentType = getServletContext().getMimeType(image.getName());
		
		//check file is image
		if(contentType == null || !contentType.startsWith("image")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		response.reset();
		response.setContentType(contentType);
		response.setHeader("Content-Lenght", String.valueOf(image.length()));
		//
		Files.copy(image.toPath(), response.getOutputStream());
		
	}


}
