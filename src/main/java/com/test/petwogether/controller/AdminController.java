package com.test.petwogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.test.petwogether.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService service;

}
