package com.koreait.matzip.user;

import com.koreait.matzip.SeacurityUtils;
import com.koreait.matzip.vo.UserVO;

public class UserService {
	// 로직담당
	
	private UserDAO dao;
	
	public UserService() {
		dao = new UserDAO();
	}
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ가입 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	public int join(UserVO param) {
		String pw = param.getUser_pw();
		String salt = SeacurityUtils.generateSalt();
		String encryptPw = SeacurityUtils.getEncrypt(pw, salt);

		param.setUser_pw(encryptPw);
		param.setSalt(salt);
		
		return dao.join(param);
	}
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ로그인ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ	
//result (1): 로그인성공, (2):아이디없음, (3):비밀번호 틀림
	public int login(UserVO param) {
		int result = 0;
		String pw = param.getUser_pw();

		UserVO dbresult = dao.selUser(param);
		
		if(dbresult.getI_user() == 0) { //나는 getUser_id == null 이라고했었는데...
			result = 2; //아이디 없음
		} else {
			String encryptPw = SeacurityUtils.getEncrypt(pw, dbresult.getSalt());
			if(dbresult.getUser_pw().equals(encryptPw)) {
				result = 1; //로그인성공
			}
			else {
				result = 3; //비번 틀림
			}
		}
		return result;
		
	}
	
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

}
