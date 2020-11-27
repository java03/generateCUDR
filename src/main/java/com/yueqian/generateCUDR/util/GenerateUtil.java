package com.yueqian.generateCUDR.util;

import java.util.ArrayList;
import java.util.List;

public class GenerateUtil {
	
	/**
	 * 获取属性名
	 */
	public static String getFieldName(String colName) {
		// e_ename_eirst  --> eEnameFirst
		List<Integer> underLinePosi = new ArrayList<Integer>();
		int index = colName.indexOf("_");
		if(index == colName.length() - 1) {
			return colName.substring(0, colName.length()-1);
		}
		while(index != -1) {
			underLinePosi.add(index);
			index = colName.indexOf("_", index+1);
		}
		//如果没有 _ 则直接返回
		if(underLinePosi.isEmpty()) {
			return colName;
		}
		colName = colName.toLowerCase();
		//获取下划线后面的字符并变成大写
		List<String> underLineChar = new ArrayList<String>();
		for (Integer ind : underLinePosi) {
			//过滤以_结尾的
			if(ind == colName.length() - 1) {
				break;
			}
			underLineChar.add(String.valueOf(colName.charAt(ind+1)).toUpperCase());
		}
		
		//从后向前替换
		StringBuffer sbuf = new StringBuffer(colName);
		int underLineCharIndex = underLineChar.size() - 1;
		for (int i = 0; i < underLinePosi.size(); i++) {
			
			//获取反序的下划线坐标
			int revInd = underLinePosi.get(underLinePosi.size() - i - 1);
			//过滤以_结尾的
			if(revInd == colName.length() - 1) {
				sbuf.delete(sbuf.length()-1, sbuf.length());
				continue;
			}
			//替换StringBuffer中下划线以及后续一个字母的内容
			sbuf.replace(revInd, revInd+2, underLineChar.get(underLineCharIndex --));
			
		}
		return sbuf.toString();
	}


	/**
	 * 根据id查询VO方法名
	 */
	public static String getVOById(String className) {
		return "get"+className+"ById";
	}
	/**
	 * 查询List<VO>方法名
	 */
	public static String getVOList(String className) {
		return "get"+className+"s";
	}
	/**
	 * 添加VO
	 */
	public static String addVO(String className) {
		return "add"+className;
	}
	/**
	 * 删除VO
	 */
	public static String delVO(String className) {
		return "del"+className;
	}
	/**
	 * 删除VO
	 */
	public static String modifyVO(String className) {
		return "modify"+className;
	}

	/**
	 * 转换类型
	 */
	public static String convertJavaType(String typeStr) {
		typeStr = typeStr.toLowerCase();
		if(typeStr.startsWith("char") || typeStr.startsWith("varchar")) {
			return "String";
		}else if(typeStr.startsWith("int") || typeStr.matches("number(\\d+)")) {
			return "Integer";
		}else if(typeStr.startsWith("float") || typeStr.startsWith("double") ||typeStr.matches("number(\\d+\\.\\d+)")) {
			return "Double";
		}else if(typeStr.startsWith("date") || typeStr.startsWith("datetime") ) {
			return "java.util.Date";
		}
		
		return null;
	}
	
	/**
	 * 生成大驼峰名称
	 */
	public static String createGtName(String tabName) {
		String className = String.valueOf(getFieldName(tabName).charAt(0)).toUpperCase() + 
				tabName.substring(1);	
		return className;
	}
	
	/**
	 * 生成小驼峰名称
	 */
	public static String createLtName(String tabName) {
		String gtName = createGtName(tabName);
		String className = String.valueOf(gtName.charAt(0)).toLowerCase()+
				gtName.substring(1);	
		return className;
	}
}
