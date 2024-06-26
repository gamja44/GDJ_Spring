package com.winter.app.departments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//url 요청이 왔을 때 받아주는 메서드
	// @RequestParam(name ="num", defaultValue ="10"),int department_id 이름이 다를 때 사용한다.
	//위와 동일하게 생겼고 method는 생략가능하다
	@RequestMapping(value ="detail", method = RequestMethod.GET)
	public String getDetail(Model model, int department_id) throws Exception{
		//더이상 리퀘스트받아서 사용하지않는다
		DepartmentDTO departmentDTO = departmentService.getDetail(department_id);
	
		String path= "commons/message";
		
		if(departmentDTO != null) {
			model.addAttribute("dto", departmentDTO);
			path="department/detail";
		}else {
			model.addAttribute("result", "부서를 찾을수없습니다");
			model.addAttribute("url", "./list");
		}
		return path;
	}
	//add
	@RequestMapping(value="add", method = RequestMethod.GET)
	public void add() {
		
	}
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String add(DepartmentDTO departmentDTO, Model model) throws Exception {
		
		int result = departmentService.add(departmentDTO);
		String url=null;
		if(result>0) {
			url="redirect:./list";
		}else {
			url="commons/message";
			model.addAttribute("result","부서등록실패");
			model.addAttribute("url", "./list");
		}
		return url;
	}
	
	//delete
	@RequestMapping("delete")
	public String delete(DepartmentDTO departmentDTO, Model model)throws Exception {
		int result = departmentService.getDelete(departmentDTO);
		
		String url="commons/message";
		if(result>0) {
			url="redirect:./list";
		}else {
			model.addAttribute("result", "삭제실패");
			model.addAttribute("url", "./list");
		}
		return url;
	
	
	}
	//update
		@RequestMapping("update")
		public String update(int department_id, Model model)throws Exception {
		DepartmentDTO departmentDTO	= departmentService.getDetail(department_id);
		String url="commons/message";
		
		if(departmentDTO != null) {
			model.addAttribute("dto", departmentDTO);
			url = "department/update";
		}else {
			model.addAttribute("result", "없는 부서");
			model.addAttribute("url", "list");
		}
		
		return url;
		}
		
	//update	
		@RequestMapping(value = "update", method = RequestMethod.POST)
		public String update(DepartmentDTO departmentDTO)throws Exception{
			int result = departmentService.update(departmentDTO);
	
			return "redirect:list";

	
		}
	
	
}
