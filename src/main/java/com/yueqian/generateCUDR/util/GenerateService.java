package com.yueqian.generateCUDR.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class GenerateService {

	/**
	 * 生成Service interface
	 */
	public static void createService(HttpServletRequest req, String className, String creaSql, String packName) {

		StringBuffer content = new StringBuffer();
		// 生成类名
		String serviceName = className + "Service";
		String voName = className + "VO";
		String mapperName = className + "Mapper";
		content.append("package " + packName + ".service;\r\n\r\n")
				.append("import java.util.List;\r\n")
				.append("import " + packName + ".domain." + voName + ";\r\n\r\n")
				.append("public interface " + serviceName + " {\r\n");
		// 查询单个
		content.append("\t").append("public ").append(voName).append(" " + GenerateUtil.getVOById(voName))
				.append("(String id);\r\n\r\n");
		// 查询列表
		content.append("\t").append("public ").append("List<" + voName + ">")
				.append(" " + GenerateUtil.getVOList(voName)).append("();\r\n\r\n");
		// 添加VO
		content.append("\t").append("public ").append("int").append(" " + GenerateUtil.addVO(voName))
				.append("(" + voName + " vo);\r\n\r\n");
		// 删除VO
		content.append("\t").append("public ").append("int").append(" " + GenerateUtil.delVO(voName))
				.append("(String id);\r\n\r\n");
		// 修改VO
		content.append("\t").append("public ").append("int").append(" " + GenerateUtil.modifyVO(voName))
				.append("(" + voName + " vo);\r\n\r\n");

		content.append("}");

		// 生成mapper 接口文件
		String path = req.getServletContext().getRealPath("javaFile");
		path += "/" + packName.replace(".", "/") + "/service";
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		GenerateFile.generateFile(serviceName + ".java", path, content.toString());
	}

	/**
	 * 生成Service 接口
	 */
	public static void createServiceImpl(HttpServletRequest req, String className, String creaSql, String packName) {

		StringBuffer content = new StringBuffer();
		// 生成类名
		String serviceName = className + "ServiceImpl";
		String voName = className + "VO";
		String mapperName = className + "Mapper";
		String mapperFieldName = GenerateUtil.createLtName(mapperName);
		content.append("package " + packName + ".service.impl;\r\n\r\n")
				.append("import org.springframework.stereotype.Service;\r\n")
				.append("import javax.annotation.Resource;\r\n")
				.append("import java.util.List;\r\n")
				.append("import " + packName + ".domain." + voName + ";\r\n\r\n")
				.append("import " + packName + ".service." + className + "Service;\r\n\r\n")
				.append("import " + packName + ".mapper." + mapperName + ";\r\n\r\n")
				.append("@Service\r\n")
				.append("public class " + serviceName + " implements "+className + "Service{\r\n")
				.append("\t@Resource\r\n")
				.append("\tprivate " + mapperName + " " + mapperFieldName).append(";\r\n\r\n");

		// 查询单个
		content.append("\t").append("public ").append(voName).append(" " + GenerateUtil.getVOById(voName))
				.append("(String id){\r\n")
				.append("\t\treturn "+mapperFieldName+"."+GenerateUtil.getVOById(voName)).append("(id);\r\n")
				.append("\t}\r\n\r\n");
		// 查询列表
		content.append("\t").append("public ").append("List<" + voName + ">")
				.append(" " + GenerateUtil.getVOList(voName)).append("(){\r\n\r\n")
				.append("\t\treturn "+mapperFieldName+"."+GenerateUtil.getVOList(voName)).append("();\r\n")
				.append("\t}\r\n\r\n");
		// 添加VO
		content.append("\t").append("public ").append("int").append(" " + GenerateUtil.addVO(voName))
				.append("(" + voName + " vo){\r\n\r\n")
				.append("\t\treturn "+mapperFieldName+"."+GenerateUtil.addVO(voName)).append("(vo);\r\n")
				.append("\t}\r\n\r\n");
		// 删除VO
		content.append("\t").append("public ").append("int").append(" " + GenerateUtil.delVO(voName))
				.append("(String id){\r\n\r\n")
				.append("\t\treturn "+mapperFieldName+"."+GenerateUtil.delVO(voName)).append("(id);\r\n")
				.append("\t}\r\n\r\n");
		// 修改VO
		content.append("\t").append("public ").append("int").append(" " + GenerateUtil.modifyVO(voName))
				.append("(" + voName + " vo){\r\n\r\n")
				.append("\t\treturn "+mapperFieldName+"."+GenerateUtil.modifyVO(voName)).append("(vo);\r\n")
				.append("\t}\r\n\r\n");

		content.append("}");
		// 生成mapper 接口文件
		String path = req.getServletContext().getRealPath("javaFile");
		path += "/" + packName.replace(".", "/") + "/service/impl";
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		GenerateFile.generateFile(serviceName + ".java", path, content.toString());
	}

}
