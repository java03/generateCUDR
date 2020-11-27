package com.yueqian.generateCUDR.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yueqian.generateCUDR.util.GenerateController;
import com.yueqian.generateCUDR.util.GenerateFile;
import com.yueqian.generateCUDR.util.GenerateJsp;
import com.yueqian.generateCUDR.util.GenerateMapper;
import com.yueqian.generateCUDR.util.GenerateMapperXML;
import com.yueqian.generateCUDR.util.GenerateService;
import com.yueqian.generateCUDR.util.GenerateUtil;
import com.yueqian.generateCUDR.util.GenerateVO;

@Controller
public class IndexController {
	
	@RequestMapping({"","/","index"})
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index.jsp");
		return mv;
	}
	
	@RequestMapping("/generate")
	@ResponseBody
	public String generate(String packName, String tabName, String creaSql, HttpServletRequest req) {
		//校验
		if(packName == null || packName.trim().isEmpty() ||
				tabName == null || tabName.trim().isEmpty() ||
						creaSql == null || creaSql.trim().isEmpty() ) {
			return "failed"; 
		}
		
		System.out.println(packName);
		System.out.println(tabName);
		System.out.println(creaSql);
		
		//生成sql文件
		String path = req.getServletContext().getRealPath("sqlFile");
		GenerateFile.generateFile(tabName+".sql", path, "CREATE TABLE "+tabName+"("+creaSql.replace("&", " ").replace("$", ",")+")");
		//类的基础名
		String className = GenerateUtil.createGtName(GenerateUtil.getFieldName(tabName));
		//生成VO类
		GenerateVO.createVO(req, className, creaSql, packName);
		//生成Mapper接口
		GenerateMapper.createMapper(req, className, creaSql, packName);
		//生成Mapper.xml
		GenerateMapperXML.createMapperXML(req, className, creaSql, packName, tabName);
		//生成Service
		GenerateService.createService(req, className, creaSql, packName);
		GenerateService.createServiceImpl(req, className, creaSql, packName);
		//生成index.jsp
		GenerateJsp.generateIndexJsp(req, className, creaSql, packName);
		//生成Controller
		GenerateController.createController(req, className, creaSql, packName);
		//生成add.jsp
		GenerateJsp.generateAddVO(req, className, creaSql, packName);
		//生成修改页面
		GenerateJsp.generateModifyAndAddJsp(req, className, creaSql, packName);
		
		return "success";
	}

	
	
	
	
	

}
