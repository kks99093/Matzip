package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

public class LoginChekInterceptor {
	//null이 넘어가면 아무일도 없음
	//문자열이 리턴되면(null이 아닌값) 그 문자열로 sendRedirect 함
	
	public static String routerChk(HttpServletRequest request) {
		//로그인이 되어 있으면 login, join 접속 X
		//로그인에 따른 접속 가능여부 판단
		//(로그인이 안되어 있으면 접속할 수 있는 주소만 여기로 체크, 나머지 전부 로그인이 되어 있어야함)
		String[] chkUserUriArr = {"login", "loginProc", "join", "joinProc", "ajaxIdChk"};
		boolean isLogout = SeacurityUtils.isLogout(request);
		String[] targetUri = request.getRequestURI().split("/");
		
		if(targetUri.length < 3){
			return null;
			
		}
		
		//로그아웃 상태
		if(isLogout) {			
			if(targetUri[1].equals(ViewRef.URI_USER)) {
				for(String uri: chkUserUriArr) {
					 if(uri.equals(targetUri[2])){
						 return null;
					 }
				}
			}
			return "/user/login";
		} else {
			//로그인 상태
			if(targetUri[1].equals(ViewRef.URI_USER)) {
				for(String uri: chkUserUriArr) {
					 if(uri.equals(targetUri[2])){
						 return "/restaurant/restMap";
					 }
				}
			}
			return null;
		}
	}
}
