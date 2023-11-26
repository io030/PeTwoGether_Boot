package com.test.petwogether.model.admin;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AdminDAOImpl implements AdminDAO{
	
	   
	   @Autowired
	   private SqlSessionTemplate sql;

	   @Override
	   public AdminDTO aLoginCheck(AdminDTO dto) {
	      
	      return this.sql.selectOne("admin.auth", dto);
	   }
	   
}
