package com.test.petwogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.test.petwogether.service.PetSitterService;

@Controller
public class PetSitterController {

	@Autowired
	private PetSitterService service;
}	
