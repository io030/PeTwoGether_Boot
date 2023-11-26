package com.test.petwogether.model.petsitter;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class PetSitterDAOimpl implements PetSitterDAO  {
	
	@Autowired
	private SqlSessionTemplate sql;
	
	@Override
	public PetSitterDTO pLoginCheck(PetSitterDTO dto) {

		return sql.selectOne("petsitter.auth", dto);
	}
	
}
