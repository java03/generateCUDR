package com.yueqian.generateCUDR.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class GenerateJsp {
	/**
	 * 生成index Jsp
	 */
	public static void generateIndexJsp(HttpServletRequest req, String className, String creaSql, String packName) {
		
		StringBuffer content = new StringBuffer();
		String voName = className+"VO";
		//抬头
		content.append("<%@ page language=\"java\" contentType=\"text/html; charset=utf-8\"\r\n" + 
				"    pageEncoding=\"utf-8\"%>\r\n"
				+ "<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>\r\n<!DOCTYPE html>\r\n")
		.append("<%@ taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\" %>\r\n");
		//html
		content.append("<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"<title>Index page</title>\r\n"+
				"</head>\r\n" + 
				"<body>\r\n");
		//table
		content.append("<table border=\"1\" align=\"center\" width=\"80%\">\r\n"+
				"\t<thead>\r\n"+
				"\t\t<tr>\r\n\t\t\t");
		//表头
		String[] cols = creaSql.split("\\$");
		for (String col : cols) {
			String[] cs = col.split("&");
			//列名
			String fieldName = GenerateUtil.getFieldName(cs[0]);
			content.append("<th>"+fieldName+"</th>");
		}
		//操作列
		content.append("<th>操作</th>\r\n");
		content.append("\t\t</tr>\r\n\t</thead>\r\n");
		//内容
		content.append("\t<tbody>\r\n\t<c:forEach items=\"${vos}\" var=\"vo\">\r\n"
				+ "\t\t<tr>\r\n\t\t\t");
		String idName = null;
		for (int i = 0; i<cols.length; i++) {
			String col = cols[i];
			String[] cs = col.split("&");
			//列名
			String fieldName = GenerateUtil.getFieldName(cs[0]);
			if(i == 0) {
				idName = fieldName;
			}
			if(cs[1].toLowerCase().contains("date") || cs[1].toLowerCase().contains("datetime")) {
				content.append("<td><fmt:formatDate value=\"${vo."+fieldName+"}\" pattern=\"yyyy-MM-dd\"/></td>");
				continue;
			}
			content.append("<td>${vo."+fieldName+"}</td>");
		}
		content.append("<td><a href=\"${pageContext.request.contextPath}/modifyVOPage/${vo."+idName+"}\">修改</a>"
				+ "<a href=\"${pageContext.request.contextPath}/"+GenerateUtil.delVO(voName)+"/${vo."+idName+"}\">删除</a></td>\r\n");
		content.append("\t\t</tr>\r\n").append("\t</c:forEach>\r\n").append("\t</tbody>\r\n").append("</table>\r\n");
		content.append("<p align=\"center\"><a href=\"${pageContext.request.contextPath}/addVOPage\">添加</a></p>\r\n");
		content.append("</body>\r\n</html>");
		
		
		//生成mapper 接口文件
		String path = req.getServletContext().getRealPath("pageFile");
		GenerateFile.generateFile("index.jsp", path, content.toString());
	}
	
	/**
	 * 添加页面
	 */
	public static void generateAddVO(HttpServletRequest req, String className, String creaSql, String packName) {
		generateModifyAndAddJsp(req, className, creaSql, packName, false);
	}
	
	/**
	 * 修改页面
	 */
	public static void generateModifyAndAddJsp(HttpServletRequest req, String className, String creaSql, String packName) {
		generateModifyAndAddJsp(req, className, creaSql, packName, true);
	}
	
	private static void generateModifyAndAddJsp(HttpServletRequest req, String className, String creaSql, String packName, boolean modifyFlag) {
		
		StringBuffer content = new StringBuffer();
		String voName = className+"VO";
		//抬头
		content.append("<%@ page language=\"java\" contentType=\"text/html; charset=utf-8\"\r\n" + 
				"    pageEncoding=\"utf-8\"%>\r\n"
				+ "<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>\r\n<!DOCTYPE html>\r\n")
		.append("<%@ taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\" %>\r\n");
		//html
		content.append("<html>\r\n" + 
				"\t<head>\r\n" + 
				"\t\t<meta charset=\"utf-8\">\r\n"); 
		if(modifyFlag) {
			content.append("\t\t<title>Modify "+className+"VO page</title>\r\n");
		}else {
			content.append("\t\t<title>Add "+className+"VO page</title>\r\n");
		}
		content.append("\t</head>\r\n" + 
				"\t<body>\r\n");
		content.append("\t\t<div align=\"center\">\r\n"); 
		//form
		if(modifyFlag) {
			content.append("\t\t\t<form method=\"post\" action=\"${pageContext.request.contextPath}/"+GenerateUtil.modifyVO(voName)+"\">\r\n");
		}else {
			content.append("\t\t\t<form method=\"post\" action=\"${pageContext.request.contextPath}/"+GenerateUtil.addVO(voName)+"\">\r\n");
		}
		//表单内容
		String[] cols = creaSql.split("\\$");
		int index = 0;
		for (String col : cols) {
			String[] cs = col.split("&");
			//列名
			String fieldName = GenerateUtil.getFieldName(cs[0]);
			if(index == 0 && modifyFlag == true) {
				index++;
				content.append("\t\t\t\t<label>"+fieldName+":</label><input type=\"text\" name=\""+fieldName+"\" readonly=\"readonly\" value=\"${modVO."+fieldName+"}\"><br>\r\n");
				continue;
			}else if(modifyFlag == true) {
				if(cs[1].toLowerCase().contains("date") || cs[1].toLowerCase().contains("datetime")) {
					content.append("\t\t\t\t<label>"+fieldName+":</label><input type=\"text\" name=\""+fieldName+"\" value=\"<fmt:formatDate value='${modVO."+fieldName+"}' pattern='yyyy-MM-dd'/>\"><br>\r\n");
				}else {
					content.append("\t\t\t\t<label>"+fieldName+":</label><input type=\"text\" name=\""+fieldName+"\" value=\"${modVO."+fieldName+"}\"><br>\r\n");
				}
			}else {
				content.append("\t\t\t\t<label>"+fieldName+":</label><input type=\"text\" name=\""+fieldName+"\"><br>\r\n");
			}
		}
		//提交按钮
		content.append("\t\t\t\t<input type=\"submit\" value=\"添加\">\r\n");
		content.append("\t\t\t</form>\r\n" + 
				"\t\t</div>\r\n");
		content.append("\t</body>\r\n</html>");
		
		
		//生成文件
		String path = req.getServletContext().getRealPath("pageFile");
		if(modifyFlag) {
			GenerateFile.generateFile("modify"+GenerateUtil.createGtName(className)+".jsp", path, content.toString());
			return;
		}
		GenerateFile.generateFile("add"+GenerateUtil.createGtName(className)+".jsp", path, content.toString());
	}

}
