package com.yueqian.generateCUDR.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class GenerateMapper {
	/**
	 * 生成Mapper interface
	 */
	public static void createMapper(HttpServletRequest req, String className, String creaSql, String packName) {
		
		StringBuffer content = new StringBuffer();
		//生成VO类名
		String voName = className +"VO";
		content.append("package "+packName+".mapper;\r\n\r\n")
		.append("import java.util.List;\r\n")
		.append("import "+packName+".domain."+voName+";\r\n\r\n")
		.append("public interface "+className+"Mapper {\r\n");
		//查询单个
		content.append("\t").append("public ").append(voName).append(" "+GenerateUtil.getVOById(voName)).append("(String id);\r\n\r\n");
		//查询列表
		content.append("\t").append("public ").append("List<"+voName+">").append(" "+GenerateUtil.getVOList(voName)).append("();\r\n\r\n");
		//添加VO
		content.append("\t").append("public ").append("int").append(" "+GenerateUtil.addVO(voName)).append("("+voName+" vo);\r\n\r\n");
		//删除VO
		content.append("\t").append("public ").append("int").append(" "+GenerateUtil.delVO(voName)).append("(String id);\r\n\r\n");
		//修改VO
		content.append("\t").append("public ").append("int").append(" "+GenerateUtil.modifyVO(voName)).append("("+voName+" vo);\r\n\r\n");
		
		content.append("}");
		
		//生成mapper 接口文件
		String path = req.getServletContext().getRealPath("javaFile");
		path += "/" + packName.replace(".", "/") + "/mapper";
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		GenerateFile.generateFile(className + "Mapper.java", path, content.toString());
	}

}
