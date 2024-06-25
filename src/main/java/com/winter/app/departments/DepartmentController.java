package com.winter.app.departments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/department/*")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	//ioc: inversion of controll 스프링프레임워크에게 위임해준다
	//어노테이션 설명과 실행의 의미
	//자바에서는 객체생성하고 메서드를 호출
	//@controller, service, repository, component(3가지아닌 그외것일때 사용)
	
	@RequestMapping(value = "list",method = RequestMethod.GET)
	public void getList() throws Exception {
		System.out.println("department list");
		departmentService.getList();
	}
	
}
