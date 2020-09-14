package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.restaurant.RestaurantController;
import com.koreait.matzip.user.UserController;

public class HandlerMapper {
	private UserController userCon;	
	private RestaurantController restCon;
	
	public HandlerMapper() {
		userCon = new UserController();
		restCon = new RestaurantController();
	}
	
	
	public String nav(HttpServletRequest request) {
		
		//여기서 주소값 분석을 할거
		String[] uriArr = request.getRequestURI().split("/");
		// 슬러쉬(/)기준으로 잘라서 저장[/res/js/test.js => 0번방이 빈칸/ res-1번/ js -2번/ test.js - 3번]
		if(uriArr.length < 3) {
			return "405";
		}
		for(int i=0; i<uriArr.length; i++) {
			System.out.println(uriArr[i]);
		}
		switch(uriArr[1]) { //1번방은 컨트롤러 어떤컨트롤러인지 여기서 구분
		case ViewRef.URI_USER :			//user
			switch(uriArr[2]) { //2번방은 메소드(어떤 template(jsp파일)을 열지)
			case "login":
				return userCon.login(request);
			case "loginProc":
				return userCon.loginProc(request);
			case "join":
				return userCon.join(request);
			case "joinProc":
				return userCon.joinProc(request);
			case "ajaxChk":
				return userCon.ajaxIdChk(request);
			case "logout":
				return userCon.logout(request);
			
			}
		case ViewRef.URI_RESTAURANT:
			switch(uriArr[2]) {
			case "restMap":
				return restCon.restMap(request);
			case "restReg":
				return restCon.restReg(request);
			case "restProc":
				return restCon.restProc(request);
			case "ajaxGetList":
				return restCon.ajaxGetList(request);
			case "restDetail":
				return restCon.restDetail(request);
			case "addRecMenusProc":
				return restCon.addRecMenusProc(request);
			}
		}
		
		return "404";
	}

}
