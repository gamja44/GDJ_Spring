package com.winter.app.locations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/location/*")
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public void getList(Model model) throws Exception {
		//데이터가져와서 콘솔찍기
		//locationService.getList();
		
		List<LocationDTO> ar= locationService.getList();
		//지정한 이름으로 값사용하기
		model.addAttribute("list", ar);
	}
	@RequestMapping(value="detail", method = RequestMethod.GET)
	public void getDetail(Model model, int location_id) throws Exception {
		
		LocationDTO locationDTO = locationService.getDetail(location_id);
		model.addAttribute("dto", locationDTO);
	}
	@RequestMapping(value="add", method = RequestMethod.GET)
	public void add() {
		
	}
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String add2(LocationDTO locationDTO, Model model)throws Exception {
		
		int result = locationService.add(locationDTO);
		String url=null;
		if(result>0) {
			url="redirect:./list";
		}else {
			url="commons/message";
			model.addAttribute("result", "등록실패");
			model.addAttribute("url", "./list");
		}
		return url;
		
	}
}
