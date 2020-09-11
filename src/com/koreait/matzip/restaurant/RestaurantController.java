package com.koreait.matzip.restaurant;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.SeacurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.RestaurantVO;

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
	
	public String ajaxGetList(HttpServletRequest request) {
		return "ajax:" + service.getRestList();//아작스로 리턴해줌
	}
}
