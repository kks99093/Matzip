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
				//param = dbresult 하면 안되는이유
				//param은 컨트롤러의 param의 주소값을 갖고있었는데 저렇게 해보리면
				//dbresult의 주소값으로 바껴버리기때문
				
				//여기서 param에다가 그냥 담은다음에 controller에서 세션에 박기
				param.setUser_pw(null);
				param.setI_user(dbresult.getI_user());
				param.setNm(dbresult.getNm());
				param.setProfile_img(dbresult.getProfile_img());
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
