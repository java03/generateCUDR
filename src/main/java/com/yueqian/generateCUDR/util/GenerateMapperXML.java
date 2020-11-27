package com.yueqian.generateCUDR.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class GenerateMapperXML {
	
	/**
	 * Mapper.xml
	 */
	public static void createMapperXML(HttpServletRequest req, String className, String creaSql, String packName, String tableName) {
		String[] cols = creaSql.split("\\$");
		StringBuffer content = new StringBuffer();
		//生成VO类名
		String voName = className +"VO";
		
		//xml
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<!DOCTYPE mapper PUBLIC \r\n" + 
				"\"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n" + 
				"\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
		content.append("<mapper namespace=\""+packName+".mapper."+className+"Mapper\">\r\n");
		
		//resultMap
		resultMapXML(className, cols, content, voName);
		
		//查询单个
		selectVOByIdXML(className, tableName, cols, content);
		
		//查询列表
		selectVOListXML(className, tableName, cols, content);
		
		//删除
		delVOXML(className, tableName, cols, content);
		//修改
		modifyVOXML(className, tableName, cols, content);
		//添加
		addVOXML(className, tableName, cols, content);
		
		content.append("</mapper>");
		
		//生成mapper xml文件
		String path = req.getServletContext().getRealPath("javaFile");
		path += "/" + packName.replace(".", "/") + "/mapper";
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		GenerateFile.generateFile(className + "Mapper.xml", path, content.toString());
	}


	/**
	 * resultMap
	 */
	private static void resultMapXML(String className, String[] cols, StringBuffer content, String voName) {
		content.append("\t<resultMap type=\""+voName+"\" id=\""+className+"Map\">\r\n");
		//注意：将第一个列作为id
		for (int i = 0 ; i < cols.length; i++) {
			String[] cs = cols[i].split("&");
			//属性名
			String fieldName = GenerateUtil.getFieldName(cs[0]);
			//列名
			String colName = cs[0];
			//属性Java类型
			String javaType = GenerateUtil.convertJavaType(cs[1]);
			//属性的类类型
			String colType = cs[1];
			
			//id
			if(i == 0) {
				content.append("\t\t<id property=\""+fieldName+"\" column=\""+colName+"\"/>\r\n");
				continue;
			}
			content.append("\t\t<result property=\""+fieldName+"\" column=\""+colName+"\"/>\r\n");
			
		}
		content.append("\t</resultMap>\r\n");
	}

	/**
	 * 查询单个
	 */
	private static void selectVOByIdXML(String className, String tableName, String[] cols, StringBuffer content) {
		String voName = className + "VO";
		content.append("\t<select id=\""+GenerateUtil.getVOById(voName)+"\" resultMap=\""+className+"Map\" parameterType=\"string\">\r\n");
		content.append("\t\tSELECT ");
		String idCol = "";
		for (int i = 0 ; i < cols.length; i++) {
			String[] cs = cols[i].split("&");
			if(i == 0) {
				idCol = cs[0];
			}
			//列名
			String colName = cs[0];
			content.append(" "+colName);
			if( i != cols.length - 1) {
				content.append(",");
			}
		}
		content.append(" FROM ").append(tableName).append(" WHERE "+idCol+" = #{id}");
		content.append("\r\n\t</select>\r\n");
	}
	
	/**
	 * 查询列表
	 */
	private static void selectVOListXML(String className, String tableName, String[] cols, StringBuffer content) {
		String voName = className + "VO";
		content.append("\t<select id=\""+GenerateUtil.getVOList(voName)+"\" resultMap=\""+className+"Map\">\r\n");
		content.append("\t\tSELECT ");
		String idCol = "";
		for (int i = 0 ; i < cols.length; i++) {
			String[] cs = cols[i].split("&");
			if(i == 0) {
				idCol = cs[0];
			}
			//列名
			String colName = cs[0];
			content.append(" "+colName);
			if( i != cols.length - 1) {
				content.append(",");
			}
		}
		content.append(" FROM ").append(tableName);
		content.append("\r\n\t</select>\r\n");
	}
	
	/**
	 * 删除
	 */
	private static void delVOXML(String className, String tableName, String[] cols, StringBuffer content) {
		content.append("\t<delete id=\""+GenerateUtil.delVO(className)+"VO\" parameterType=\"string\">\r\n");
		content.append("\t\tDELETE FROM "+tableName+" WHERE "+cols[0].split("&")[0]+" = #{id}\r\n");
		content.append("\t</delete>\r\n");
	}
	
	/**
	 * modify xml
	 */
	private static void modifyVOXML(String className, String tableName, String[] cols, StringBuffer content) {
		content.append("\t<update id=\""+GenerateUtil.modifyVO(className)+"VO\" parameterType=\""+className+"VO\">\r\n");
		content.append("\t\tUPDATE "+tableName+" SET ");
		String idCol = "";
		for (int i = 0 ; i < cols.length; i++) {
			String[] cs = cols[i].split("&");
			if(i == 0) {
				idCol = cs[0];
			}
			//列名
			String colName = cs[0];
			content.append(" "+colName+"=#{"+GenerateUtil.getFieldName(colName)+"}");
			if( i != cols.length - 1) {
				content.append(", ");
			}
		}
		content.append(" WHERE ").append(idCol+" = #{"+GenerateUtil.getFieldName(idCol)+"}");
		content.append("\r\n\t</update>\r\n");
	}
	
	/**
	 * 添加
	 */
	private static void addVOXML(String className, String tableName, String[] cols, StringBuffer content) {
		content.append("\t<insert id=\""+GenerateUtil.addVO(className)+"VO\" parameterType=\""+className+"VO\">\r\n");
		content.append("\t\tINSERT INTO "+tableName+"(");
		StringBuffer valuesBuf = new StringBuffer(" VALUES(");
		String idCol = "";
		for (int i = 0 ; i < cols.length; i++) {
			String[] cs = cols[i].split("&");
			//列名
			String colName = cs[0];
			content.append(" "+colName);//+"=#{"+GenerateUtil.getFieldName(colName)+"}");
			valuesBuf.append("#{"+GenerateUtil.getFieldName(colName)+"}");
			if( i != cols.length - 1) {
				content.append(", ");
				valuesBuf.append(", ");
			}
		}
		content.append(") ").append(valuesBuf).append(")");
		content.append("\r\n\t</insert>\r\n");
	}

}
