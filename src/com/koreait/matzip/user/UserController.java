package com.koreait.matzip.user;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.UserVO;

public class UserController {
	// 맵핑담당(넘어온값을 담아서 보낸다)
	
	private UserService service;
	
	public UserController() {
		service = new UserService();
	}

//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ로그인ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
//	/user/login
	public String login(HttpServletRequest request) {
		String error = request.getParameter("error");		
		if(error != null) {
			switch(error) {
			case "2":
				request.setAttribute("msg", "아이디를 확인해 주세요.");
				break;
			case "3":
				request.setAttribute("msg", "비밀번호를 확인해 주세요.");
				break;
			}
		}
		// 열어야할 파일과 열어야할 template을 적어줄거임
		request.setAttribute(Const.TITLE, "로그인");
		request.setAttribute(Const.VIEW, "user/login");
		// 어떤걸 jsp에서 include 해줄것인가 , 템플릿(상,하,메뉴)는 고정되고 그안에 바뀌는 페이지를 여기 적어줌
		// jsp에서 include를 하면 여기에 적어준 user/login.jsp파일이 열린다
		return ViewRef.TEMP_DEFAULT; // 템플릿 안씀(디폴트 템플릿 사용)
		// 리턴해주는값은 어떤 템플릿을 열것인가

		// 이제 화면을 열때 얘네3개는 필수
	}
	
	public String loginProc(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw(user_pw);
		
		int result = service.login(param);
		if(result == 1) {
			return "redirect:/restaurant/restMap";
		}else {
			return "redirect:/user/login?error=" + result;
		}

	}
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ가입ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	public String join(HttpServletRequest request) {
		request.setAttribute(Const.TITLE, "회원가입");
		request.setAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}

	public String joinProc(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw"); // 암호화
		String nm = request.getParameter("nm");

		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw(user_pw);
		param.setNm(nm);
		
		int result = service.join(param);
		
		return "redirect:/user/login";
	}
//ㅡㅡㅡㅡㅡㅡㅡ아이디중복체크 Ajax ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	public String ajaxIdChk(HttpServletRequest request) {
		String user_id =request.getParameter("user_id");
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_pw("");
		
		int result = service.login(param);
				
		return String.format("ajax:{\"result\": %s}", result);
		//result : 2 아니면 3이 넘어올거다
	}

}
