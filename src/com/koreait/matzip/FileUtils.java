package com.koreait.matzip;

import java.io.File;

public class FileUtils {
	public static void makeFolder(String path) {
		File dir = new File(path);
		if(!dir.exists()) {
		//만약 폴더가 없다면
			
			dir.mkdirs();
		//폴더를 만듬
		}
	}

	public static String getExt(String fileNm) {
		return fileNm.substring(fileNm.lastIndexOf("."));
	}
}
