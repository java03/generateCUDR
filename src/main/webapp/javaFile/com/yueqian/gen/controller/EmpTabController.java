package com.yueqian.gen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import com.yueqian.gen.service.EmpTabService;
import com.yueqian.gen.domain.EmpTabVO;

@Controller
public class EmpTabController {
	@Resource
	private EmpTabService empTabService;
	@RequestMapping({"/","","/index"})
	public String getEmpTabVOs(ModelMap mm){

		mm.addAttribute("vos", empTabService.getEmpTabVOs());

		return "/index.jsp";

	}

	@RequestMapping("/addVOPage")
	public String addEmpTabVO(){

		return "/addEmpTab.jsp";

	}

	@RequestMapping("/addEmpTabVO")
	public String addEmpTabVO(EmpTabVO vo, ModelMap mm){

		if(empTabService.addEmpTabVO(vo) > 0){
			return "redirect:/";
		}
		mm.addAttribute("errMsg", "添加失败");

		return "forward:/addVOPage";
	}

	@RequestMapping("/delEmpTabVO/{id}")
	public String delEmpTabVO(@PathVariable String id, ModelMap mm){

		if(empTabService.delEmpTabVO(id) > 0){
			return "redirect:/";
		}
		mm.addAttribute("errMsg", "删除失败");

		return "forward:/";
	}

	@RequestMapping("/modifyVOPage/{id}")
	public String modifyEmpTabVO(@PathVariable String id, ModelMap mm){

		mm.addAttribute("modVO", empTabService.getEmpTabVOById(id));

		return "/modifyEmpTab.jsp";

	}

	@RequestMapping("/modifyEmpTabVO")
	public String modifyEmpTabVO(EmpTabVO vo, ModelMap mm){

		if(empTabService.modifyEmpTabVO(vo) > 0){
			return "redirect:/";
		}
		mm.addAttribute("errMsg", "修改失败");

		return "forward:/modifyVOPage";
	}

}