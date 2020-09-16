package com.koreait.matzip;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.koreait.matzip.vo.UserVO;

public class SeacurityUtils {
	
	//세션에서 I_user 불러오는 메소드를 따로 만듬
	public static int getLoginUserPk(HttpServletRequest request) {
		return getLoginUser(request).getI_user();
	}
	
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ로그인 판단ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	
	public static UserVO getLoginUser(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		return (UserVO) hs.getAttribute(Const.LOGIN_USER);
	}

	public static boolean isLogout(HttpServletRequest request) {
		return getLoginUser(request) == null;
	}

	
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ비밀번호 암호화 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	public static String getEncrypt(String source, String salt) {
		return getEncrypt(source, salt.getBytes());
	}

	public static String getEncrypt(String source, byte[] salt) {

		String result = "";

		byte[] a = source.getBytes();
		byte[] bytes = new byte[a.length + salt.length];

		System.arraycopy(a, 0, bytes, 0, a.length);
		System.arraycopy(salt, 0, bytes, a.length, salt.length);

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);

			byte[] byteData = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
			}

			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String generateSalt() {
		Random random = new Random();

		byte[] salt = new byte[8];
		random.nextBytes(salt);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < salt.length; i++) {
			// byte 값을 Hex 값으로 바꾸기.
			sb.append(String.format("%02x", salt[i]));
		}

		return sb.toString();
	}

}
