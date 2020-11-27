package com.yueqian.generateCUDR.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;



public class GenerateController {
	/**
	 * 生成Controller
	 */
	public static void createController(HttpServletRequest req, String className, String creaSql, String packName) {
		
		StringBuffer content = new StringBuffer();
		//生成VO类名
		String controllerName = className +"Controller";
		String voName = className +"VO";
		String serviceName = className+"Service";
		String serviceObjName = GenerateUtil.createLtName(serviceName);
		content.append("package "+packName+".controller;\r\n\r\n")
		.append("import org.springframework.stereotype.Controller;\r\n")
		.append("import org.springframework.ui.ModelMap;\r\n")
		.append("import org.springframework.web.bind.annotation.PathVariable;\r\n")
		.append("import javax.annotation.Resource;\r\n")
		.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n")
		.append("import java.util.List;\r\n")
		.append("import "+packName+".service."+serviceName+";\r\n")
		.append("import "+packName+".domain."+voName+";\r\n\r\n")
		.append("@Controller\r\n")
		.append("public class "+className+"Controller {\r\n");
		//注入Service
		content.append("\t@Resource\r\n")
		.append("\tprivate "+serviceName+" "+serviceObjName+";\r\n");
		
		//查询列表
		queryListRequestMapping(content, voName, serviceObjName);
		
		//跳转添加页面
		addVORequestMapping(content, voName, serviceObjName);
		
		//删除VO
		delVORequestMapping(content, voName, serviceObjName);
		//修改VO
		modifyVORequestMapping(content, voName, serviceObjName);
		
		content.append("}");
		
		//生成mapper 接口文件
		String path = req.getServletContext().getRealPath("javaFile");
		path += "/" + packName.replace(".", "/") + "/controller";
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		GenerateFile.generateFile(controllerName + ".java", path, content.toString());
	}

	/**
	 * 修改VO  /modifyVO.jsp   modVO
	 */
	private static void modifyVORequestMapping(StringBuffer content, String voName, String serviceObjName) {
		content.append("\t@RequestMapping(\"/modifyVOPage/{id}\")\r\n");
		content.append("\t").append("public String ").append(GenerateUtil.modifyVO(voName)).append("(@PathVariable String id, ModelMap mm){\r\n\r\n")
			.append("\t\tmm.addAttribute(\"modVO\", "+serviceObjName+"."+GenerateUtil.getVOById(voName)+"(id));\r\n\r\n")
			.append("\t\treturn \"/modify"+GenerateUtil.createGtName(voName.substring(0, voName.length()-2))+".jsp\";\r\n\r\n")
			.append("\t}\r\n\r\n");
		//添加VO
		content.append("\t@RequestMapping(\"/"+GenerateUtil.modifyVO(voName)+"\")\r\n");
		content.append("\t").append("public String ").append(GenerateUtil.modifyVO(voName)).append("("+voName+" vo, ModelMap mm){\r\n\r\n")
		.append("\t\tif("+serviceObjName+"."+GenerateUtil.modifyVO(voName)+"(vo) > 0){\r\n")
		.append("\t\t\treturn \"redirect:/\";\r\n")
		.append("\t\t}\r\n")
		.append("\t\tmm.addAttribute(\"errMsg\", \"修改失败\");\r\n\r\n")
		.append("\t\treturn \"forward:/modifyVOPage\";\r\n")
		.append("\t}\r\n\r\n");
	}

	/**
	 * 删除VO
	 */
	private static void delVORequestMapping(StringBuffer content, String voName, String serviceObjName) {
		content.append("\t@RequestMapping(\"/"+GenerateUtil.delVO(voName)+"/{id}\")\r\n");
		content.append("\t").append("public String ").append(GenerateUtil.delVO(voName)).append("(@PathVariable String id, ModelMap mm){\r\n\r\n")
				
		.append("\t\tif("+serviceObjName+"."+GenerateUtil.delVO(voName)+"(id) > 0){\r\n")
		.append("\t\t\treturn \"redirect:/\";\r\n")
		.append("\t\t}\r\n")
		.append("\t\tmm.addAttribute(\"errMsg\", \"删除失败\");\r\n\r\n")
		.append("\t\treturn \"forward:/\";\r\n")
		.append("\t}\r\n\r\n");
	}

	/**
	 * 跳转添加页面 /addVO.jsp
	 */
	private static void addVORequestMapping(StringBuffer content, String voName, String serviceObjName) {
		content.append("\t@RequestMapping(\"/addVOPage\")\r\n");
		content.append("\t").append("public String ").append(GenerateUtil.addVO(voName)).append("(){\r\n\r\n")
			.append("\t\treturn \"/add"+GenerateUtil.createGtName(voName.substring(0, voName.length()-2))+".jsp\";\r\n\r\n")
			.append("\t}\r\n\r\n");
		//添加VO
		content.append("\t@RequestMapping(\"/"+GenerateUtil.addVO(voName)+"\")\r\n");
		content.append("\t").append("public String ").append(GenerateUtil.addVO(voName)).append("("+voName+" vo, ModelMap mm){\r\n\r\n")
		.append("\t\tif("+serviceObjName+"."+GenerateUtil.addVO(voName)+"(vo) > 0){\r\n")
		.append("\t\t\treturn \"redirect:/\";\r\n")
		.append("\t\t}\r\n")
		.append("\t\tmm.addAttribute(\"errMsg\", \"添加失败\");\r\n\r\n")
		.append("\t\treturn \"forward:/addVOPage\";\r\n")
		.append("\t}\r\n\r\n");
	}

	/**
	 * 查询列表 /index.jsp
	 */
	private static void queryListRequestMapping(StringBuffer content, String voName, String serviceObjName) {
		content.append("\t@RequestMapping({\"/\",\"\",\"/index\"})\r\n");
		content.append("\t").append("public String ").append(GenerateUtil.getVOList(voName)).append("(ModelMap mm){\r\n\r\n")
		.append("\t\tmm.addAttribute(\"vos\", "+serviceObjName+"."+GenerateUtil.getVOList(voName)+"());\r\n\r\n")
		.append("\t\treturn \"/index.jsp\";\r\n\r\n")
		.append("\t}\r\n\r\n");
	}

}
