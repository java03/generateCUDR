package com.yueqian.generateCUDR.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class GenerateFile {
	
	/**
	 * 生成文件
	 */
	public static boolean generateFile(String fileName, String path, String content) {
		
		try {
			FileUtils.write(new File(path+"/"+fileName), content, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
