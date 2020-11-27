package com.yueqian.generateCUDR.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class GenerateVO {
	
	/**
	 * 生成VO
	 */
	public static void createVO(HttpServletRequest req, String className, String creaSql, String packName) {
		
		String[] cols = creaSql.split("\\$");
		StringBuffer content = new StringBuffer();
		StringBuffer methodBuf = new StringBuffer();
		content.append("package "+packName+".domain;\r\n").append("public class "+className+"VO {\r\n");
		//生成属性和方法
		for (String col : cols) {
			String[] cs = col.split("&");
			
			//属性
			String fieldName = GenerateUtil.getFieldName(cs[0]);
			String typeStr = GenerateUtil.convertJavaType(cs[1]);
			content.append("\tprivate ").append(typeStr).append(" ").append(fieldName).append(";\r\n");
			//get方法
			methodBuf.append("\t").append("public ").append(typeStr).append(" get"+getMethodName(fieldName)).append("(){\r\n");
			methodBuf.append("\t\t").append("return this."+fieldName+";\r\n");
			methodBuf.append("\t").append("}\r\n\r\n");
			//set方法
			methodBuf.append("\t").append("public void").append(" set"+getMethodName(fieldName)).append("( "+typeStr+" "+fieldName+" ){\r\n");
			methodBuf.append("\t\t").append("this."+fieldName+" = "+fieldName+";\r\n");
			methodBuf.append("\t").append("}\r\n\r\n");
		}
		content.append("\r\n").append(methodBuf).append("}");
		//生成mapper 接口文件
		String path = req.getServletContext().getRealPath("javaFile");
		path += "/"+packName.replace(".", "/")+"/domain";
		File filePath = new File(path);
		if(!filePath.exists()) {
			filePath.mkdirs();
		}
		GenerateFile.generateFile(className+"VO.java", path, content.toString());
	}
	
	/**
	 * 处理当setXXX getXXX 的方法名，如果第二个字母大写，则首字母不能大写
	 */
	public static String getMethodName(String fieldName) {
		if(fieldName == null || fieldName.isEmpty()) {
			return null;
		}
		if(fieldName.length() == 1) {
			return GenerateUtil.createGtName(fieldName);
		}else if(String.valueOf(fieldName.charAt(1)).equals(String.valueOf(fieldName.charAt(1)).toUpperCase())) {
			System.out.println(fieldName+"-------------");
			return fieldName;
		}else {
			return GenerateUtil.createGtName(fieldName);
		}
	}

}
