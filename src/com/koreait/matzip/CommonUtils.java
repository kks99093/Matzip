package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonUtils {
	public static int paraseStrToInt(String str) {
		return paraseStrToInt(str, 0);
	}	
	
	public static int paraseStrToInt(String str, int n1) {
		try {
			return Integer.parseInt(str);
		}catch(Exception e){
			return n1;
		}
	}
	
	public static int getIntParameter(HttpServletRequest request, String keyNm) {
		return paraseStrToInt(request.getParameter(keyNm));
	}
	
	public static double paraseStrToDouble(String str) {
		return paraseStrToDouble(str, 0);
	}
	
	public static double paraseStrToDouble(String str, double n1) {
		try {		
			return Double.parseDouble(str);
		}catch(Exception e){
			return n1;
		}
	}
	public static double getDoubleParameter(HttpServletRequest request, String keyNm) {
		return paraseStrToDouble(request.getParameter(keyNm));
	}
}
