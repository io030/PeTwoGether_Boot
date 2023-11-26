package com.test.petwogether.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.test.petwogether.model.petsitter.PetSitterDAO;

@Service
@Primary
public class PetSitterServiceImpl implements PetSitterService {

		@Autowired
		private PetSitterDAO dao;
}
