package com.example.vmedico.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
	
//	@ExceptionHandler(value = SizeLimitExceededException.class)
//	public ModelAndView  handleMultipartException(SizeLimitExceededException ex) {
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("erroroccuredinuploading","File size is big... cannot be uploaded");
//		mv.setViewName("prescriptionupload.jsp");
//		return mv;
//	}
	
	@ExceptionHandler(value = Exception.class)
	public String  handleNullPointerException(Exception ex) {
		ex.printStackTrace();
		return "redirect:home";
	}
	

}
