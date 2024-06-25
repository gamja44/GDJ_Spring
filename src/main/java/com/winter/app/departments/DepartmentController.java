package com.winter.app.departments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public void getList(Model model) throws Exception {
		System.out.println("department list");
		List<DepartmentDTO> ar= departmentService.getList();
//모델앤뷰객체만들기		
//		ModelAndView mv= new ModelAndView();
//		mv.addObject("list", ar);
//		return mv;
		
		//모델하나만 매개변수로 넣기
		model.addAttribute("list", ar);
	}
	
}
