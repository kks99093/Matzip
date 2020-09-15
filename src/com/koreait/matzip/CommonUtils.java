package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

public class CommonUtils {
	public static int parseStrToInt(String str) {
		return parseStrToInt(str, 0);
	}	
	
	public static int parseStrToInt(String str, int n1) {
		try {
			return Integer.parseInt(str);
		}catch(Exception e){
			return n1;
		}
	}
	
	public static int getIntParameter(HttpServletRequest request, String keyNm) {
		return parseStrToInt(request.getParameter(keyNm));
	}
	
	public static int getIntParameter(MultipartRequest request, String keyNm) {
		return parseStrToInt(request.getParameter(keyNm));
	}
	
	public static double parseStrToDouble(String str) {
		return parseStrToDouble(str, 0);
	}
	
	public static double parseStrToDouble(String str, double n1) {
		try {		
			return Double.parseDouble(str);
		}catch(Exception e){
			return n1;
		}
	}
	public static double getDoubleParameter(HttpServletRequest request, String keyNm) {
		return parseStrToDouble(request.getParameter(keyNm));
	}
}
