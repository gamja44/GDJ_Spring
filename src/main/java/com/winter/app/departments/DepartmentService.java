package com.winter.app.departments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	public void getList() throws Exception {
		departmentDAO.getList();
	}
}
