package com.koreait.matzip.restaurant;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.SeacurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.koreait.matzip.vo.UserVO;

public class RestaurantController {
	
	private RestaurantService service;
	
	public RestaurantController() {
		service = new RestaurantService();
	}
	
	public String restMap(HttpServletRequest request) {
			request.setAttribute(Const.TITLE, "지도 보기");
			request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU;
	}
	
	public String restReg(HttpServletRequest request) {

		final int I_M = 1; //카테고리 코드
		request.setAttribute("categoryList", CommonDAO.selCodeList(I_M));		
		request.setAttribute(Const.TITLE, "가게 등록");
		request.setAttribute(Const.VIEW, "restaurant/restReg");
	return ViewRef.TEMP_MENU;
	}
	
	//리스트
	public String restProc(HttpServletRequest request) {
		
		int category = CommonUtils.getIntParameter(request, "cd_category");
		double lat = CommonUtils.getDoubleParameter(request, "lat");
		double lng = CommonUtils.getDoubleParameter(request, "lng");
		RestaurantVO param = new RestaurantVO();
		request.getParameter("cd_category");
		param.setNm(request.getParameter("nm"));
		param.setCd_category(category);
		param.setAddr(request.getParameter("addr"));
		param.setLat(lat);
		param.setLng(lng);
		param.setI_user(SeacurityUtils.getLoginUser(request).getI_user());
		
		int result = service.restProc(param);	
		return "redirect:/restaurant/restMap";
	}
	//리스트 ajax
	public String ajaxGetList(HttpServletRequest request) {
		return "ajax:" + service.getRestList();//아작스로 리턴해줌
	}
	
	//디테일
	public String restDetail(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter(request, "i_rest");

		RestaurantVO param = new RestaurantVO();
		param.setI_rest(i_rest);
		
		request.setAttribute("css", new String[] {"restaurant"});
		//request에 String 배열을 써서 css넣기 menuTemp 참조(c:forEach) 사용
		request.setAttribute("recommendMenuList", service.getRecommendMenuList(i_rest));
		request.setAttribute("menuList", service.getMenuList(i_rest));
		request.setAttribute("data", service.restDetail(param));
		request.setAttribute(Const.TITLE, "디테일");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		return ViewRef.TEMP_MENU;
	}
	//추천 메뉴 추가
	public String addRecMenusProc(HttpServletRequest request) {	
		int i_rest = service.addRecMenus(request);
			
		return "redirect:/restaurant/restDetail?i_rest="+i_rest;
	}
	
	//추천메뉴 삭제
	public String ajaxDelRecMenu(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter(request, "i_rest");
		int seq = CommonUtils.getIntParameter(request, "seq");
		int i_user = SeacurityUtils.getLoginUserPk(request);
		
		RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
		param.setI_user(i_user);
		param.setI_rest(i_rest);
		param.setSeq(seq);
		
		int result = service.delRecMenu(param);
		return "ajax:" + result;
	}
	
	//메뉴 추가
	public String addMenuProc(HttpServletRequest request) {
		int i_rest = service.addMenus(request);
		return "redirect:/restaurant/restDetail?i_rest="+i_rest;
	}

}
