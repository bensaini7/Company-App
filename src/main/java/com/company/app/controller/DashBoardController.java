package com.company.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
/**
 * This is a non RestController and caters a light HTML
 * Angular-JS based client to see, edit and update 
 * companies saved in the database.
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
@Controller
public class DashBoardController {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView renderDashBoard(){
		ModelAndView response;
		
		response = new ModelAndView(new InternalResourceView("/resources/static/index.htm"));
		
		return response;
	}
}
