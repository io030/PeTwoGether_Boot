package com.test.petwogether.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.test.petwogether.model.admin.AdminDAO;

@Service
@Primary
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO dao;

}
